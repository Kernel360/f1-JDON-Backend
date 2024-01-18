package kernel.jdon.inflearncourse.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InflearncourseErrorCode implements ErrorCode {

	NOT_FOUND_INFLEARN_COURSE(HttpStatus.NOT_FOUND, "존재하지 않는 인프런 강의입니다.");

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
