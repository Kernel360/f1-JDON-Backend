package kernel.jdon.moduleapi.domain.favorite.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode, BaseThrowException<FavoriteErrorCode.FavoriteBaseException> {
    NOT_FOUND_FAVORITE(HttpStatus.NOT_FOUND, "존재하지 않는 찜 정보입니다."),
    LECTURE_NOT_FAVORITED(HttpStatus.CONFLICT, "찜하지 않은 강의는 찜 취소할 수 없습니다.");

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
    public FavoriteBaseException throwException() {
        return new FavoriteBaseException(this);
    }

    public class FavoriteBaseException extends ApiException {
        public FavoriteBaseException(FavoriteErrorCode favoriteErrorCode) {
            super(favoriteErrorCode);
        }
    }

}
