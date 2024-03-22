package kernel.jdon.modulebatch.domain.inflearncourse.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InflearnErrorCode implements ErrorCode {
    NOT_FOUND_INFLEARN_URL(HttpStatus.NOT_FOUND, "존재하지 않는 인프런 강의 url 입니다.");

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
