package kernel.jdon.error.exception.api;

import kernel.jdon.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private transient ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
