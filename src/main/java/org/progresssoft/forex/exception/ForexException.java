package org.progresssoft.forex.exception;

public class ForexException extends Exception {

	private static final long serialVersionUID = 8361853635545295474L;

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

}
