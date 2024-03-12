package kernel.jdon.moduleapi.domain.jd.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JdErrorCode implements ErrorCode, BaseThrowException<JdErrorCode.JobBaseException> {
    NOT_FOUND_JD(HttpStatus.NOT_FOUND, "존재하지 않는 채용 공고 입니다.");

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
    public JobBaseException throwException() {
        return new JobBaseException(this);
    }

    public class JobBaseException extends ApiException {
        public JobBaseException(JdErrorCode skillErrorCode) {
            super(skillErrorCode);
        }
    }
}
