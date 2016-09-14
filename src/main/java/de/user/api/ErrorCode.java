package de.user.api;

/**
 * The same as an {@code InternalErrorCode} but you can overwrite the message.
 *
 */
public class ErrorCode extends BasicDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5550287435509417857L;

	private final int status;

	private final String code;

	private final String message;

	private final String moreInfo;

	/**
	 * Create a new ErrorCode object.
	 * 
	 * @param status
	 *            the status
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param moreInfo
	 *            additional info
	 */
	public ErrorCode(final int status, final String code, final String message, final String moreInfo) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
		this.moreInfo = moreInfo;
	}

	/**
	 * @return the status
	 */
	public final int getStatus() {
		return status;
	}

	/**
	 * @return the code
	 */
	public final String getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @return the moreInfo
	 */
	public final String getMoreInfo() {
		return moreInfo;
	}

}
