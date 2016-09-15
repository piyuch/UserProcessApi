package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the user email is not verified yet
 * 
 * @author piyush
 *
 */
public class UserMailNotVerifiedYetException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -325666867155430663L;

	public UserMailNotVerifiedYetException(String message, Throwable parent) {
		super(message, parent);
	}

	public UserMailNotVerifiedYetException(Throwable parent) {
		super(parent);
	}

	public UserMailNotVerifiedYetException(String message) {
		super(message);
	}
}
