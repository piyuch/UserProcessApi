package de.user.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.exception.user.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureException;

/**
 * Filter to set an updated JWT on the response for use of future requests.
 *
 */
@Provider
@JWTSecured
public class JWTResponseFilter implements ContainerResponseFilter {

	private static final Logger LOG = LogManager.getLogger(JWTResponseFilter.class);

	private static final String AUTHORIZATION = "Authorization";
	private static final String X_TOKEN = "X-TOKEN";

	@Inject
	private JWTCreator jwtCreator;

	/**
	 * Take the JWT from the request, extract user and role and create a new one
	 * with updated expiration date.
	 * 
	 * @param requestContext
	 *            the incoming request context.
	 * @param responseContext
	 *            the response context.
	 * @throws IOException
	 *             if an I/O exception occurs.
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
			throws IOException {
		String token = requestContext.getHeaderString(AUTHORIZATION);
		if (null != token) {
			try {
				Jws<Claims> parseClaimsJws = jwtCreator.parse(token);

				if (parseClaimsJws == null) {
					throw new InvalidTokenException("The token is invalid");
				}

				Claims principal = parseClaimsJws.getBody();
				String username = principal.getSubject();
				String s = jwtCreator.createAuthToken(username);

				responseContext.getHeaders().add(X_TOKEN, s);
			} catch (SignatureException e) {
				LOG.error("Could not parse JWT.");
			} catch (ExpiredJwtException e) {
				LOG.error("The token already expired.");
			} catch (InvalidTokenException e) {
				LOG.error("The token is invalid");
			}
		}
	}
}
