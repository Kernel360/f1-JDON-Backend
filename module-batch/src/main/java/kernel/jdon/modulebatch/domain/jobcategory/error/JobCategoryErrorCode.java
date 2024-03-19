package kernel.jdon.modulebatch.domain.jobcategory.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulebatch.global.exception.BatchException;
import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JobCategoryErrorCode
    implements ErrorCode, BaseThrowException<JobCategoryErrorCode.JobCateogryBaseException> {
    NOT_FOUND_JOB_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 직군 또는 직무입니다.");

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
    public JobCateogryBaseException throwException() {
        return new JobCateogryBaseException(this);
    }

    public class JobCateogryBaseException extends BatchException {
        public JobCateogryBaseException(JobCategoryErrorCode jobCategoryErrorCode) {
            super(jobCategoryErrorCode);
        }
    }
}
