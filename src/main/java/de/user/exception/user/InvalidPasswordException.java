package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the password is invalid
 * 
 * @author piyush
 *
 */
public class InvalidPasswordException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 7885322679367024186L;

	public InvalidPasswordException(String message, Throwable parent) {
		super(message, parent);
	}

	public InvalidPasswordException(Throwable parent) {
		super(parent);
	}

	public InvalidPasswordException(String message) {
		super(message);
	}
}
