package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the email is invalid
 * 
 * @author piyush chand
 *
 */
public class InvalidEmailException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -8533902426219984933L;

	public InvalidEmailException(String message, Throwable parent) {
		super(message, parent);
	}

	public InvalidEmailException(Throwable parent) {
		super(parent);
	}

	public InvalidEmailException(String message) {
		super(message);
	}
}
