package kernel.jdon.error.exception.crawler;

import kernel.jdon.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class CrawlerException extends RuntimeException {
	private final transient ErrorCode errorCode;

	public CrawlerException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
