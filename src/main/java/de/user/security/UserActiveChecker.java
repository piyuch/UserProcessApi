package de.user.security;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.api.request.UserLoginRequest;
import de.user.control.UserControl;
import de.user.exception.user.UserNotFoundException;
import de.user.model.UserEntity;

/**
 * This is an Intercepter class that checks the user is active.
 * 
 * @author piyushchand
 *
 */

@Interceptor
@CheckUserActive
public class UserActiveChecker {

	private static final Logger LOG = LogManager.getLogger(UserActiveChecker.class);

	@Inject
	private UserControl userControl;

	@AroundInvoke
	public Object checkUserFullyRegistered(final InvocationContext invocationContext) throws Exception {

		LOG.info("Interceptor to check user is active.");
		for (int i = 0; i < invocationContext.getParameters().length; i++) {
			if (invocationContext.getParameters()[i] instanceof UserLoginRequest) {
				UserLoginRequest userLoginRequest = (UserLoginRequest) invocationContext.getParameters()[i];
				UserEntity user = userControl.findUserByEmail(userLoginRequest.getEmail());
				if (user != null) {
					// checks if the user is active
					if (user.isDeactivated()) {
						throw new UserNotFoundException("User is deactivated." + user.getId());
					}
				}
			}
		}
		return invocationContext.proceed();
	}
}
