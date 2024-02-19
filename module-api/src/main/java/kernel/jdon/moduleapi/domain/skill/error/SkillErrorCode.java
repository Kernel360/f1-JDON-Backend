package kernel.jdon.moduleapi.domain.skill.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkillErrorCode implements ErrorCode {
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
}
