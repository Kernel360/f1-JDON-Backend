package kernel.jdon.modulecommon.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();

    String getMessage();
}
