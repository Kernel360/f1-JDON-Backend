package kernel.jdon.moduleapi.domain.review.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode, BaseThrowException<ReviewErrorCode.ReviewBaseException> {
	NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰 입니다."),
	FORBIDDEN_DELETE_REVIEW(HttpStatus.FORBIDDEN, "리뷰 삭제 권한이 없습니다.");

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
	public ReviewBaseException throwException() {
		return new ReviewBaseException(this);
	}

	public class ReviewBaseException extends ApiException {
		public ReviewBaseException(ReviewErrorCode reviewErrorCode) {
			super(reviewErrorCode);
		}
	}

}
