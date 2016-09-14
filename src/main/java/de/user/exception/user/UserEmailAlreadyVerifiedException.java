package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the user email is already verified
 * 
 * @author hazem
 *
 */
public class UserEmailAlreadyVerifiedException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -5384748886549740624L;

	public UserEmailAlreadyVerifiedException(String message, Throwable parent) {
		super(message, parent);
	}

	public UserEmailAlreadyVerifiedException(Throwable parent) {
		super(parent);
	}

	public UserEmailAlreadyVerifiedException(String message) {
		super(message);
	}
}
