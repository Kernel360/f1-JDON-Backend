package kernel.jdon.modulecrawler.domain.jd.core.fetchmanager;

import kernel.jdon.modulecrawler.global.config.ScrapingWantedProperties;
import kernel.jdon.modulecrawler.global.exception.CrawlerException;
import kernel.jdon.modulecrawler.global.exception.CrawlerServerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JobDetailFetchManager {
    private final ScrapingWantedProperties scrapingWantedProperties;
    private int sleepCount = 0;

    public void incrementSleepCounter() {
        sleepCount++;
        if (isSleepRequired()) {
            performSleep();
            resetSleepCount();
        }
    }

    private void resetSleepCount() {
        sleepCount = 0;
    }

    private boolean isSleepRequired() {
        return this.sleepCount >= scrapingWantedProperties.getJobDetailSleepCount();
    }

    public boolean isDuplicateRequired(final int count) {
        return count >= scrapingWantedProperties.getJobDetailDuplicateLimitCount();
    }

    private void performSleep() {
        try {
            Thread.sleep(scrapingWantedProperties.getSleepTimeMillis());
        } catch (InterruptedException e) {
            log.error("JobListFetchManager.performSleep {}", "Thread sleep 중 Error 발생");
            throw new CrawlerException(CrawlerServerErrorCode.INTERNAL_SERVER_ERROR_THREAD_SLEEP);
        }
    }
}
