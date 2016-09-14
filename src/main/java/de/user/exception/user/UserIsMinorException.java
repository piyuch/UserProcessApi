package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * 
 * Exception used when a WhitelabelUser age is under 18
 * 
 * @author hazem
 *
 */
public class UserIsMinorException extends BusinessException {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 8774690207041942026L;

	/**
	 * @param message
	 *            The detail message.
	 */
	public UserIsMinorException(final String message) {
		super(message);
	}

}
