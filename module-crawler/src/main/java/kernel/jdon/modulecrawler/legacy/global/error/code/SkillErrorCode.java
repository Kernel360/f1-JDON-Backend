package kernel.jdon.modulecrawler.legacy.global.error.code;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SkillErrorCode implements ErrorCode {
    NOT_FOUND_SKILL(HttpStatus.NOT_FOUND, "존재하지 않는 기술 스택입니다.");

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
