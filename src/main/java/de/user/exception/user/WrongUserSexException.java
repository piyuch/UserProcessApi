package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the user sex is wrong
 * 
 * @author piyush
 *
 */
public class WrongUserSexException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 700488886661767241L;

	public WrongUserSexException(String message, Throwable parent) {
		super(message, parent);
	}

	public WrongUserSexException(Throwable parent) {
		super(parent);
	}

	public WrongUserSexException(String message) {
		super(message);
	}
}
