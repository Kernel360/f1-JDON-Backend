package kernel.jdon.auth.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
	//
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
	UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE(HttpStatus.UNAUTHORIZED, "다른 소셜 로그인으로 가입된 이메일입니다."),
	UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL(HttpStatus.UNAUTHORIZED, "소셜 서비스에 이메일을 등록해야 서비스를 이용할 수 있습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다.");

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
