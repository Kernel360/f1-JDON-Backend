package kernel.jdon.modulecrawler.legacy.global.error.code;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WantedErrorCode implements ErrorCode {
    NOT_FOUND_JOB_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 직군 또는 직무 입니다.");

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
