package kernel.jdon.moduleapi.domain.inflearncourse.error;

import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InflearncourseErrorCode
	implements ErrorCode, BaseThrowException<InflearncourseErrorCode.InflearncourseBaseException> {

	NOT_FOUND_INFLEARN_COURSE(HttpStatus.NOT_FOUND, "존재하지 않는 인프런 강의입니다.");

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
	public InflearncourseBaseException throwException() {
		return new InflearncourseBaseException(this);
	}

	public class InflearncourseBaseException extends ApiException {
		public InflearncourseBaseException(InflearncourseErrorCode inflearncourseErrorCode) {
			super(inflearncourseErrorCode);
		}
	}

}
