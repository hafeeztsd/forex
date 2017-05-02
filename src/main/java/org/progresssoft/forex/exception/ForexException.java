package org.progresssoft.forex.exception;

/**
 * All the exception will be wrapped into this object.
 * 
 * @author hafeeztsd
 *
 */
public class ForexException extends Exception {

	private static final long serialVersionUID = 8361853635545295474L;

	private ForexErrorCode forexErrorCode;

	public ForexException(ForexErrorCode forexErrorCode) {
		this.forexErrorCode = forexErrorCode;
	}

	public ForexException(ForexErrorCode forexErrorCode, Throwable cause) {
		super(cause);
		this.forexErrorCode = forexErrorCode;
	}

	public ForexException() {
		super();
	}

	public ForexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ForexException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForexException(String message) {
		super(message);
	}

	public ForexException(Throwable cause) {
		super(cause);
	}

	public ForexErrorCode getForexErrorCode() {
		return forexErrorCode;
	}

}
