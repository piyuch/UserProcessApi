package de.user.exception.user;

import de.user.exception.BusinessException;

/**
 * Exception used when the data set by the user is missing
 * 
 * @author piyush
 *
 */
public class MissingDataException extends BusinessException {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -6507603945711908051L;

	public MissingDataException(String message, Throwable parent) {
		super(message, parent);
	}

	public MissingDataException(Throwable parent) {
		super(parent);
	}

	public MissingDataException(String message) {
		super(message);
	}
}
