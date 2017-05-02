package org.progresssoft.forex.exception;

/**
 * Forex error code contains an error code along with the default message.
 * Client can use its own error message based on error code.
 * 
 * @author hafeeztsd
 *
 */
public enum ForexErrorCode {

	ALREADY_UPLOADED(100, "Given file has already uploaded."), SYSTEM_ERROR(101,
			"System is not able to process records.");

	private int code;
	private String message;

	private ForexErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
