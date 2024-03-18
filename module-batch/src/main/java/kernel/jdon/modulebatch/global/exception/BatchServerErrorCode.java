package kernel.jdon.modulebatch.global.exception;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BatchServerErrorCode implements ErrorCode, BaseThrowException<BatchServerErrorCode.SkillBaseException> {
    INTERNAL_SERVER_ERROR_THREAD_SLEEP(HttpStatus.INTERNAL_SERVER_ERROR, "Thread sleep 중 서버 애러가 발생했습니다.");

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
    public SkillBaseException throwException() {
        return new SkillBaseException(this);
    }

    public class SkillBaseException extends BatchException {
        public SkillBaseException(BatchServerErrorCode skillErrorCode) {
            super(skillErrorCode);
        }
    }
}
