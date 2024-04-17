package kernel.jdon.modulecrawler.domain.jd.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import kernel.jdon.modulecrawler.global.exception.CrawlerException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JdErrorCode implements ErrorCode, BaseThrowException<JdErrorCode.JobBaseException> {
    CONFLICT_FAIL_LOCK(HttpStatus.CONFLICT, "다른 요청에 의해 이미 스케줄링 작업이 진행중 입니다."),
    THREAD_INTERRUPTED(HttpStatus.SERVICE_UNAVAILABLE, "처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");

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

    public class JobBaseException extends CrawlerException {
        public JobBaseException(JdErrorCode skillErrorCode) {
            super(skillErrorCode);
        }
    }
}
