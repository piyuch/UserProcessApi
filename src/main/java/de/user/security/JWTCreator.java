package de.user.security;

import java.time.Instant;
import java.util.Date;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.boundary.UserBoundary;
import de.user.exception.user.InvalidTokenException;
import de.user.properties.PropertiesHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Responsible for the creation of Java Web Tokens.
 *
 */
public final class JWTCreator {

	private static final Logger LOG = LogManager.getLogger(UserBoundary.class);

	@Inject
	private PropertiesHandler propertiesHandler;

	/**
	 * Creates an JWT Auth token from email
	 * 
	 * @param email
	 *            the email to use
	 * @return the generated token
	 */
	public String createAuthToken(final String email) {
		LOG.entry(email);
		Claims claims = new DefaultClaims();
		claims.setSubject(email);
		final long expiration = Long.valueOf(propertiesHandler.getProperty("jwt.ttl.auth"));
		return createToken(email, claims, expiration);
	}

	/**
	 * Creates a JWT token for verifying an email address
	 * 
	 * @param email
	 *            the email to use
	 * @param attempted
	 *            the link attempted by user before registration
	 * @return the generated token
	 */
	public String createEmailVerificationToken(final String email) {
		LOG.entry(email);
		Claims claims = new DefaultClaims();
		claims.setSubject(email);
		final long expiration = Long.valueOf(propertiesHandler.getProperty("jwt.ttl.verifymail"));
		return createToken(email, claims, expiration);
	}

	/**
	 * Create a JWT Token to reset a password
	 * 
	 * @param email
	 * @return the generated token
	 */
	public String createResetPasswordToken(final String email) {
		LOG.entry(email);
		Claims claims = new DefaultClaims();
		claims.setSubject(email);
		final long expiration = Long.valueOf(propertiesHandler.getProperty("jwt.ttl.resetpassword"));
		return createToken(email, claims, expiration);
	}

	/**
	 * This method creates a new JWT token
	 * 
	 * @param email
	 * @param claims
	 * @param expiration
	 * @return
	 */
	private String createToken(final String email, Claims claims, final long expiration) {
		claims.setExpiration(new Date(Instant.now().plusSeconds(expiration).toEpochMilli()));
		final String key = propertiesHandler.getProperty("jwt.key");
		String s = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).compact();
		LOG.info("Created a JWT for {}", email);
		LOG.exit(s);
		return s;
	}

	/**
	 * Parse a JWT.
	 * 
	 * @param token
	 *            the token to parse
	 * @return the claims
	 */
	public Jws<Claims> parse(final String token) {
		LOG.entry(token);
		Jws<Claims> parseClaimsJws = null;
		try {
			parseClaimsJws = Jwts.parser().setSigningKey(propertiesHandler.getProperty("jwt.key"))
					.parseClaimsJws(token);
			LOG.exit(parseClaimsJws);
		} catch (MalformedJwtException e) {
			throw new InvalidTokenException("The token is invalid");
		}
		return parseClaimsJws;
	}
}
