package kernel.jdon.favorite.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {
	NOT_FOUND_FAVORITE(HttpStatus.NOT_FOUND, "존재하지 않는 찜 정보입니다.");

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
