package kernel.jdon.modulecommon.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	HttpStatus getHttpStatus();
	String getMessage();
}
