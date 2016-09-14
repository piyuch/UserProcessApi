package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * 
 * Exception used when a user already exist {@code UserEntity}.
 * 
 * @author piyushchand
 *
 */
public class UserAlreadyExistException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 2403390954464268380L;

	/**
	 * @param message
	 *            The detail message.
	 */
	public UserAlreadyExistException(final String message) {
		super(message);
	}

}
