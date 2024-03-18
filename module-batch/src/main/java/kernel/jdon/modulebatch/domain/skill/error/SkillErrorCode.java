package kernel.jdon.modulebatch.domain.skill.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulebatch.global.exception.BaseThrowException;
import kernel.jdon.modulebatch.global.exception.BatchException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkillErrorCode implements ErrorCode, BaseThrowException<SkillErrorCode.SkillBaseException> {
    NOT_FOUND_SKILL(HttpStatus.NOT_FOUND, "존재하지 않는 기술 스킬입니다.");

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
        public SkillBaseException(SkillErrorCode skillErrorCode) {
            super(skillErrorCode);
        }
    }
}
