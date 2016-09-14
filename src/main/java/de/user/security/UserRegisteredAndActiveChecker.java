package de.user.security;

import java.security.Principal;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.SecurityContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.boundary.UserBoundary;
import de.user.control.UserControl;
import de.user.exception.user.UserMailNotVerifiedYetException;
import de.user.exception.user.UserNotFoundException;
import de.user.model.UserEntity;

/**
 * This is an Interceptor class that checks for Fully registered users
 * 
 * @author piyushchand
 *
 */

@Interceptor
@CheckUserRegisteredAndActive
public class UserRegisteredAndActiveChecker {

	private static final Logger LOG = LogManager.getLogger(UserRegisteredAndActiveChecker.class);

	@Inject
	private UserBoundary userBoundary;

	@Inject
	private UserControl userControl;

	@AroundInvoke
	public Object checkUserFullyRegistered(final InvocationContext invocationContext) throws Exception {

		LOG.info("Interceptor to check for fully registerd user.");
		for (int i = 0; i < invocationContext.getParameters().length; i++) {
			if (invocationContext.getParameters()[i] instanceof SecurityContext) {
				SecurityContext securityContext = (SecurityContext) invocationContext.getParameters()[i];
				Principal principal = securityContext.getUserPrincipal();

				long userId = userBoundary.getPrincipalUserId(principal);
				UserEntity user = userControl.findUserById(userId);
				if (user != null) {
					// checks if the user's email is verified, if not an
					// exception is thrown
					if (!user.isMailVerified()) {
						throw new UserMailNotVerifiedYetException("User`s email is not verified yet." + user.getId());
					}
					// checks if the user is registered, if not an exception is
					// thrown
					if (!user.isRegistered()) {
						throw new UserNotFoundException("User Not Registered Yet." + user.getId());
					}
					// checks if the user is active
					if (user.isDeactivated()) {
						throw new UserNotFoundException("User is deactivated." + user.getId());
					}

				} else {
					// throws an exception if user is not found
					throw new UserNotFoundException("User not found!");
				}
			}
		}
		return invocationContext.proceed();
	}
}
