/**
 * 
 */
package de.user.security;

import java.security.Principal;
import java.util.Set;

import javax.ws.rs.core.SecurityContext;

/**
 * Custom SecurityContext for the JWT and HMAC Filter.
 */
public class Authorizer implements SecurityContext {

	private final String scheme;
	private final Set<String> roles;
	private final String username;
	private final boolean isSecure;

	/**
	 * Create a new Authorizer.
	 * 
	 * @param roles
	 *            the roles
	 * @param username
	 *            the username
	 * @param isSecure
	 *            was the request secure, e.g. https
	 * @param scheme
	 *            the name of the scheme
	 */
	public Authorizer(final Set<String> roles, final String username, final boolean isSecure, final String scheme) {
		this.roles = roles;
		this.username = username;
		this.isSecure = isSecure;
		this.scheme = scheme;
	}

	@Override
	public Principal getUserPrincipal() {
		return new User(username);
	}

	@Override
	public boolean isUserInRole(final String role) {
		return roles.contains(role);
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return scheme;
	}

	/**
	 * A user used in the Security context.
	 *
	 */
	public static class User implements Principal {
		private String name;

		/**
		 * Get the name of this user.
		 * 
		 * @param name
		 *            the name of the user
		 */
		public User(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

}
