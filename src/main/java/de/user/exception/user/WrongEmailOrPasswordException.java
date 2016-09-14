package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the email/password combination is wrong
 * 
 * @author hazem
 *
 */
public class WrongEmailOrPasswordException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -9165021822708661493L;

	public WrongEmailOrPasswordException(String message, Throwable parent) {
		super(message, parent);
	}

	public WrongEmailOrPasswordException(Throwable parent) {
		super(parent);
	}

	public WrongEmailOrPasswordException(String message) {
		super(message);
	}
}
