package kernel.jdon.moduleapi.domain.coffeechat.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CoffeeChatErrorCode implements ErrorCode, BaseThrowException<CoffeeChatErrorCode.CoffeeChatBaseException> {
    NOT_FOUND_COFFEECHAT(HttpStatus.NOT_FOUND, "존재하지 않는 커피챗 입니다."),
    NOT_OPEN_COFFEECHAT(HttpStatus.BAD_REQUEST, "모집중이 아닌 커피챗 입니다."),
    INVALID_TOTAL_RECRUIT_COUNT(HttpStatus.BAD_REQUEST, "총 모집인원을 현재 모집인원보다 작게 설정할 수 없습니다."),
    CANNOT_JOIN_OWN_COFFEECHAT(HttpStatus.CONFLICT, "본인이 개설한 커피챗에 참여할 수 없습니다."),
    EXPIRED_COFFEECHAT(HttpStatus.CONFLICT, "지난 일자의 커피챗은 수정할 수 없습니다."),
    MEET_DATE_ISBEFORE_NOW(HttpStatus.BAD_REQUEST, "지금보다 이전 시점으로 설정할 수 없습니다."),
    LOCK_ACQUISITION_FAILURE(HttpStatus.SERVICE_UNAVAILABLE, "현재 많은 요청으로 인해 처리가 지연되고 있습니다. 잠시 후 다시 시도해주세요."),
    THREAD_INTERRUPTED(HttpStatus.SERVICE_UNAVAILABLE, "처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요."),
    ALREADY_JOINED_COFFEECHAT(HttpStatus.CONFLICT, "이미 참여한 커피챗 입니다."),
    NOT_FOUND_APPLICATION(HttpStatus.CONFLICT, "해당 커피챗을 신청한 정보가 없습니다.");

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
    public CoffeeChatErrorCode.CoffeeChatBaseException throwException() {
        return new CoffeeChatErrorCode.CoffeeChatBaseException(this);
    }

    public class CoffeeChatBaseException extends ApiException {
        public CoffeeChatBaseException(CoffeeChatErrorCode coffeeChatErrorCode) {
            super(coffeeChatErrorCode);
        }
    }
}
