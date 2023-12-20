package kernel.jdon.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import kernel.jdon.dto.response.ErrorResponse;
import kernel.jdon.error.exception.api.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerException(Exception e, HttpServletRequest request) {
		final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(internalServerError.value())
			.body(ErrorResponse.of(internalServerError, request));
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handlerApiException(ApiException e, HttpServletRequest request) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
			.body(ErrorResponse.of(e.getErrorCode(), request));
	}
}
