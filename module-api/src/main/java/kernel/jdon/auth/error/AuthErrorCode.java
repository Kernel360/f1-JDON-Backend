package kernel.jdon.auth.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
	NOT_FOUND_NOT_MATCH_PROVIDER_TYPE(HttpStatus.NOT_FOUND, "다른 소셜 로그인으로 가입된 이메일입니다."),;

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
