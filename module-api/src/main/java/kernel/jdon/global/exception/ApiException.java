package kernel.jdon.global.exception;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private final transient ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
