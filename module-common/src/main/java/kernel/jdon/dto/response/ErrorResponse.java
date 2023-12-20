package kernel.jdon.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import kernel.jdon.error.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private String timestamp;
	private int status;
	private String message;
	private String path;

	private static String getCurrentDateTimeAsString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return now.format(formatter);
	}

	public static ErrorResponse of(HttpStatus httpStatus, HttpServletRequest request) {
		return ErrorResponse.builder()
			.timestamp(getCurrentDateTimeAsString())
			.status(httpStatus.value())
			.message(httpStatus.getReasonPhrase())
			.path(request.getRequestURI())
			.build();
	}

	public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
		return ErrorResponse.builder()
			.timestamp(getCurrentDateTimeAsString())
			.status(errorCode.getHttpStatus().value())
			.message(errorCode.getMessage())
			.path(request.getRequestURI())
			.build();
	}
}
