package kernel.jdon.faq.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.error.ErrorCode;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.global.exception.BaseThrowException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FaqErrorCode implements ErrorCode, BaseThrowException<FaqErrorCode.FaqBaseException> {
	NOT_FOUND_FAQ(HttpStatus.NOT_FOUND, "존재하지 않는 FAQ 입니다.");

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
	public FaqErrorCode.FaqBaseException throwException() {
		return new FaqErrorCode.FaqBaseException(this);
	}

	public class FaqBaseException extends ApiException {
		public FaqBaseException(FaqErrorCode faqErrorCode) {
			super(faqErrorCode);
		}
	}

}
