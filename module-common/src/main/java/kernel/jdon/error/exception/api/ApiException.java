package kernel.jdon.error.exception.api;

import kernel.jdon.error.code.ErrorCode;

public class ApiException extends RuntimeException {
	private transient ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
