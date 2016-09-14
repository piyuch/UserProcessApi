package de.user.security;

import java.io.IOException;
import java.util.HashSet;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import de.user.common.InternalErrorCodes;
import de.user.exception.user.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * An {@code ContainerRequestFilter} to check if the request provides an correct
 * JWT Token.
 * 
 * You can inject the {@code SecurityContext} into you REST Method like so:
 * 
 * <pre>
 * &#64;GET
 * public String getUsername(@Context SecurityContext securityContext) {
 * 	User user = (User) securityContext.getUserPrincipal();
 * 	return user.getName();
 * }
 * </pre>
 *
 *
 * 
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@JWTSecured
public class JWTAuthenticationFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION = "Authorization";

	@Inject
	private JWTCreator jwtCreator;

	/**
	 * Check the {@code ContainerRequestContext} and search for the
	 * authorization header. Validate the value of this header with JWT
	 * (http://jwt.io/).
	 * 
	 * If teh header is not present send {@code InternalErrorCodes.IEC_1} if the
	 * token is not valid send {@code InternalErrorCodes.IEC_10}.
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		SecurityContext originalContext = requestContext.getSecurityContext();
		String token = requestContext.getHeaderString(AUTHORIZATION);
		if (null != token) {
			try {
				Jws<Claims> parseClaimsJws = jwtCreator.parse(token);
				Claims principal = parseClaimsJws.getBody();
				String username = principal.getSubject();
				String role = (String) principal.get("role");
				HashSet<String> roles = new HashSet<String>();
				roles.add(role);
				Authorizer authorizer = new Authorizer(roles, username, originalContext.isSecure(), "JWT-Based-Scheme");
				requestContext.setSecurityContext(authorizer);
			} catch (SignatureException e) {
				throw new NotAuthorizedException(Response.status(InternalErrorCodes.IEC_3.getStatus())
						.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_3.getCode())).build());
			} catch (ExpiredJwtException e) {
				throw new NotAuthorizedException(Response.status(InternalErrorCodes.IEC_3.getStatus())
						.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_3.getCode())).build());
			} catch (MalformedJwtException e) {
				throw new NotAuthorizedException(Response.status(InternalErrorCodes.IEC_3.getStatus())
						.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_3.getCode())).build());
			} catch (InvalidTokenException e) {
				throw new NotAuthorizedException(Response.status(InternalErrorCodes.IEC_4.getStatus())
						.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_4.getCode())).build());
			}
		} else {
			throw new NotAuthorizedException(Response.status(InternalErrorCodes.IEC_4.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_4.getCode())).build());
		}

	}

}
