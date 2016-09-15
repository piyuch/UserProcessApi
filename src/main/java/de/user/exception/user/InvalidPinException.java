package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the PIN is invalid
 * 
 * @author piyush
 *
 */
public class InvalidPinException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -7478684269391448023L;

	public InvalidPinException(String message, Throwable parent) {
		super(message, parent);
	}

	public InvalidPinException(Throwable parent) {
		super(parent);
	}

	public InvalidPinException(String message) {
		super(message);
	}
}
