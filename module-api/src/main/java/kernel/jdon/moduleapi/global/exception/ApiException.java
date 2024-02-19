package kernel.jdon.moduleapi.global.exception;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private final transient ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ApiException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}
}
