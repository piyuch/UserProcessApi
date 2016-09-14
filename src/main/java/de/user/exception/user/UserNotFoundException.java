/**
 * 
 */
package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when we can't find a {@code UserEntity}.
 */
public class UserNotFoundException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -1612344196231908089L;

	/**
	 * @param message
	 *            The detail message.
	 */
	public UserNotFoundException(final String message) {
		super(message);
	}

}
