package kernel.jdon.error.code.api;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.code.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FaqErrorCode implements ErrorCode {
	NOT_FOUND_FAQ(HttpStatus.NOT_FOUND, "존재하지 않는 FAQ 입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
