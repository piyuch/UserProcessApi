package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the user is already registered
 * 
 * @author hazem
 *
 */
public class UserAlreadyRegisteredException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -3446416510479322457L;

	public UserAlreadyRegisteredException(String message, Throwable parent) {
		super(message, parent);
	}

	public UserAlreadyRegisteredException(Throwable parent) {
		super(parent);
	}

	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
