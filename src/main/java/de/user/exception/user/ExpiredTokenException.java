package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the token is expired.
 * 
 * @author piyush
 *
 */
public class ExpiredTokenException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 7957692901981322077L;

	/**
	 * @param message
	 *            The detail message.
	 */
	public ExpiredTokenException(final String message) {
		super(message);
	}

}
