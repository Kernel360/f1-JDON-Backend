package kernel.jdon.modulebatch.global.exception;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BatchServerErrorCode
    implements ErrorCode, BaseThrowException<BatchServerErrorCode.BatchServerBaseException> {
    INTERNAL_SERVER_ERROR_THREAD_SLEEP(HttpStatus.INTERNAL_SERVER_ERROR, "Thread sleep 중 서버 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR_REST_TEMPLATE_RETRY(HttpStatus.INTERNAL_SERVER_ERROR, "RestTemplate Retry 중 서버 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR_SCHEDULER(HttpStatus.INTERNAL_SERVER_ERROR, "스케줄링에 의해 Job 실행 중 에러가 발생했습니다.");

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
    public BatchServerBaseException throwException() {
        return new BatchServerBaseException(this);
    }

    public class BatchServerBaseException extends BatchException {
        public BatchServerBaseException(BatchServerErrorCode batchServerErrorCode) {
            super(batchServerErrorCode);
        }
    }
}
