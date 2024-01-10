package kernel.jdon.error.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import kernel.jdon.dto.response.ErrorResponse;
import kernel.jdon.error.exception.api.ApiException;
import kernel.jdon.error.exception.crawler.CrawlerException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handlerApiException(ApiException e, HttpServletRequest request) {
		log.warn(e.getErrorCode().getMessage(), e);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
			.body(ErrorResponse.of(e.getErrorCode(), request));
	}

	@ExceptionHandler(CrawlerException.class)
	public ResponseEntity<ErrorResponse> handlerApiException(CrawlerException e, HttpServletRequest request) {
		log.warn(e.getErrorCode().getMessage(), e);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
			.body(ErrorResponse.of(e.getErrorCode(), request));
	}
}
