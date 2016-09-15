package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the user email is cannot be verified
 * 
 * @author piyush
 *
 */
public class UserEmailCouldNotBeVerifiedException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -7233919176499101958L;

	public UserEmailCouldNotBeVerifiedException(String message, Throwable parent) {
		super(message, parent);
	}

	public UserEmailCouldNotBeVerifiedException(Throwable parent) {
		super(parent);
	}

	public UserEmailCouldNotBeVerifiedException(String message) {
		super(message);
	}
}
