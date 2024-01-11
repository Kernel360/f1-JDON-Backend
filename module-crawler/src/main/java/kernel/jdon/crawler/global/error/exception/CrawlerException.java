package kernel.jdon.crawler.global.error.exception;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class CrawlerException extends RuntimeException {
	private final transient ErrorCode errorCode;

	public CrawlerException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
