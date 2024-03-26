package kernel.jdon.moduleapi.global.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import kernel.jdon.modulecommon.dto.response.ErrorResponse;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
        HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        String firstErrorMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(e.getStatusCode())
            .body(ErrorResponse.of(e.getStatusCode(), firstErrorMessage, request));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlerConstraintViolationException(ConstraintViolationException e,
        HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        final Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findFirst();
        final String firstErrorMessage = constraintViolation.isPresent() ? constraintViolation.get().getMessage()
            : e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, firstErrorMessage, request));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthException e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
            .body(ErrorResponse.of(e.getErrorCode(), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, request));
    }
}
