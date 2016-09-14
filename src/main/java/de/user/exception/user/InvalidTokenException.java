/**
 * 
 */
package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the token is invalid.
 */
public class InvalidTokenException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -1612344196231908089L;

	/**
	 * @param message
	 *            The detail message.
	 */
	public InvalidTokenException(final String message) {
		super(message);
	}

}
