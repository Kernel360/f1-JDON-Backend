package kernel.jdon.moduleapi.domain.member.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode,
    BaseThrowException<MemberErrorCode.MemberBaseException> {
    UNAUTHORIZED_EMAIL_OAUTH2(HttpStatus.UNAUTHORIZED, "이메일 인증에 실패하였습니다."),
    BAD_REQUEST_INVALID_ENCRYPT_STRING(HttpStatus.BAD_REQUEST, "잘못된 암호값입니다."),
    BAD_REQUEST_FAIL_PARSE_QUERY_STRING(HttpStatus.BAD_REQUEST, "쿼리스트링 파싱에 실패하였습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    CONFLICT_DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    FORBIDDEN_NOT_MATCH_EMAIL(HttpStatus.FORBIDDEN, "이메일이 일치하지 않습니다.");

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
    public MemberErrorCode.MemberBaseException throwException() {
        return new MemberErrorCode.MemberBaseException(this);
    }

    public class MemberBaseException extends ApiException {
        public MemberBaseException(MemberErrorCode memberErrorCode) {
            super(memberErrorCode);
        }
    }
}
