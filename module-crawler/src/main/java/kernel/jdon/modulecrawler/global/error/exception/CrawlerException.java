package kernel.jdon.modulecrawler.global.error.exception;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.Getter;

@Getter
public class CrawlerException extends RuntimeException {
    private final transient ErrorCode errorCode;

    public CrawlerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CrawlerException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
