package kernel.jdon.moduleapi.domain.auth.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode, BaseThrowException<AuthErrorCode.AuthBaseException> {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE(HttpStatus.UNAUTHORIZED, "다른 소셜 로그인으로 가입된 이메일입니다."),
    UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL(HttpStatus.UNAUTHORIZED, "소셜 서비스에 이메일을 등록해야 서비스를 이용할 수 있습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),
    ERROR_FAIL_TO_UNLINK_OAUTH2(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 통신 중 에러가 발생했습니다."),
    NOT_FOUND_PROVIDER(HttpStatus.NOT_FOUND, "지원되지 않는 소셜 로그인입니다."),
    CONFLICT_WITHDRAW_BY_OTHER_SOCIAL_PROVIDER(HttpStatus.CONFLICT, "다른 소셜 로그인으로 탈퇴한 내역이 존재합니다."),
    CONFLICT_WITHDRAW_ACCOUNT(HttpStatus.CONFLICT, "탈퇴한 계정입니다.");

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

    @Override
    public AuthErrorCode.AuthBaseException throwException() {
        return new AuthErrorCode.AuthBaseException(this);
    }

    public class AuthBaseException extends ApiException {
        public AuthBaseException(AuthErrorCode authErrorCode) {
            super(authErrorCode);
        }
    }
}
