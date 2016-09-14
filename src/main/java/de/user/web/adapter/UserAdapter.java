package de.user.web.adapter;

import java.security.Principal;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.user.api.PasswordTokenContainer;
import de.user.api.request.UserLoginRequest;
import de.user.api.request.UserRegisterRequest;
import de.user.api.response.UserLoginResponse;
import de.user.boundary.UserBoundary;
import de.user.model.UserEntity;
import de.user.security.JWTSecured;
import de.user.security.UserActiveChecker;

/**
 * user Registraion , Login and Password reset API
 * 
 * @author piyushchand
 *
 */

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
@Interceptors(UserActiveChecker.class)
public class UserAdapter {

	@Inject
	private UserBoundary userBoundary;

	@Context
	private HttpServletResponse response;

	private static final String X_TOKEN = "X-TOKEN";

	/**
	 * 1. Registration Step - Create a new unverified user.
	 * 
	 * @param userCredentials
	 * @return user object on success.
	 */
	@POST
	public Response registerUnverifiedUser(final UserLoginRequest user) {
		UserLoginResponse unverifiedUser = userBoundary.createUnverifiedUser(user);
		return Response.ok(unverifiedUser).build();
	}

	/**
	 * 2. Registration Step - Verify the Mail address of an user
	 * 
	 * @param token
	 * @return user object on success.
	 */
	@GET
	@Path("/emailverification/{token}")
	public Response verifyMail(@PathParam("token") final String token) {
		UserLoginResponse user = userBoundary.verifyEmail(token);
		return Response.ok(user).build();
	}

	/**
	 * 3. Registration Step - Complete user data; finish registration process
	 * 
	 * @param email
	 * @param user
	 * @return user object on success.
	 */
	@POST
	@Path("/registerwithdata")
	@JWTSecured
	public Response updateVerifiedUser(final UserRegisterRequest user, @Context SecurityContext securityContext) {
		// Get the principial from the security context & get the email (here
		// referred to as `getName()`)
		Principal principal = securityContext.getUserPrincipal();
		UserLoginResponse userToUpdate = userBoundary.updateVerifiedUser(principal.getName(), user);
		return Response.ok(userToUpdate).build();
	}

	/**
	 * User Login
	 * 
	 * @param user
	 *            login request
	 * @param maxLimitRead
	 *            the maximum limit for read events
	 * @return the Response that contains the UserLoginRespons Object and the
	 *         token in the header
	 */

	@POST
	@Path("/login")
	public Response login(final UserLoginRequest user, @QueryParam("maxlimitread") final int maxLimitRead) {
		UserLoginResponse userLoginResponse = userBoundary.authenticate(user, maxLimitRead);
		return Response.ok(userLoginResponse).header(X_TOKEN, userLoginResponse.getToken()).build();
	}

	/**
	 * This re-sends an verification link to a user
	 * 
	 * @param email
	 * @return response on success
	 */
	@GET
	@Path("/{email}/sendverificationlink")
	public Response resendVerificationMail(@PathParam("email") final String email) {
		userBoundary.resendVerificationEmail(email);
		return Response.ok().build();
	}

	/**
	 * This sends an token to reset the password of an user
	 * 
	 * @param email
	 * @return response on success
	 */
	@GET
	@Path("/{email}/passwordtoken")
	public Response sendPasswordToken(@PathParam("email") final String email) {
		UserEntity user = userBoundary.getByEmail(email);
		userBoundary.sendPasswordTokenMail(user);
		return Response.ok().build();
	}

	/**
	 * This takes a valid token and changes the password
	 * 
	 * @param passwordAndToken
	 * @return response on success
	 */
	@POST
	@Path("/passwordreset")
	public Response resetPassword(final PasswordTokenContainer passwordAndToken) {
		userBoundary.resetPassword(passwordAndToken);
		return Response.ok().build();
	}

	/**
	 * Get the authenticated user from token
	 * 
	 * @param securityContext
	 * @return userLoginResponse
	 */
	@GET
	@JWTSecured
	public Response getUserFromToken(@Context SecurityContext securityContext) {
		Principal principal = securityContext.getUserPrincipal();
		UserLoginResponse userLoginResponse = userBoundary.getPrincipalUser(principal);
		return Response.ok(userLoginResponse).build();
	}
}
