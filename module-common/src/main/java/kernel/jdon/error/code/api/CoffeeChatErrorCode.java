package kernel.jdon.error.code.api;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.code.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CoffeeChatErrorCode implements ErrorCode {
	NOT_FOUND_COFFEECHAT(HttpStatus.NOT_FOUND, "존재하지 않는 커피챗 입니다."),
	INVALID_RECRUIT_COUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 모집인원 입니다."),
	EXPIRED_COFFEECHAT(HttpStatus.BAD_REQUEST, "지난 일자의 커피챗은 수정할 수 없습니다."),
	MEET_DATE_ISBEFORE_NOW(HttpStatus.BAD_REQUEST, "지금보다 이전 시점으로 설정할 수 없습니다.");

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
