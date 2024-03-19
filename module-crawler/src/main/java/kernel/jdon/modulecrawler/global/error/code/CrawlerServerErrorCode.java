package kernel.jdon.modulecrawler.global.error.code;

import org.springframework.http.HttpStatus;

import kernel.jdon.modulecommon.error.BaseThrowException;
import kernel.jdon.modulecommon.error.ErrorCode;
import kernel.jdon.modulecrawler.global.error.exception.CrawlerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CrawlerServerErrorCode implements ErrorCode,
    BaseThrowException<CrawlerServerErrorCode.CrawlerServerBaseException> {
    INTERNAL_SERVER_ERROR_REST_TEMPLATE_RETRY(HttpStatus.INTERNAL_SERVER_ERROR, "RestTemplate Retry 중 서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public CrawlerServerBaseException throwException() {
        return new CrawlerServerBaseException(this);
    }

    public class CrawlerServerBaseException extends CrawlerException {
        public CrawlerServerBaseException(CrawlerServerErrorCode crawlerServerErrorCode) {
            super(crawlerServerErrorCode);
        }
    }
}
