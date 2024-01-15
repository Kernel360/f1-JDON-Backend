package kernel.jdon.crawler.global.error.code;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InflearnErrorCode implements ErrorCode {
	NOT_FOUND_INFLEARN_URL(HttpStatus.NOT_FOUND, "인프런 강의가 존재하지 않는 url입니다."),
	NOT_FOUND_SKILL(HttpStatus.NOT_FOUND, "존재하지 않는 Skill입니다.");

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
