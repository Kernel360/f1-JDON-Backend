package kernel.jdon.modulebatch.global.exception;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.Getter;

@Getter
public class BatchException extends RuntimeException {
    private final transient ErrorCode errorCode;

    public BatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BatchException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
