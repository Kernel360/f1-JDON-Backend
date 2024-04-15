package kernel.jdon.modulecrawler.domain.jd.core.fetchmanager;

import kernel.jdon.modulecrawler.global.config.ScrapingWantedProperties;
import kernel.jdon.modulecrawler.global.exception.CrawlerException;
import kernel.jdon.modulecrawler.global.exception.CrawlerServerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JobListFetchManager {
    private final ScrapingWantedProperties scrapingWantedProperties;
    private int offset = 0;
    private int sleepCount = 0;

    public int getOffset() {
        return this.offset;
    }

    public void incrementOffset() {
        this.offset += scrapingWantedProperties.getJobListOffset();
        incrementSleepCounter();
        if (isSleepRequired()) {
            performSleep();
            resetSleepCounter();
        }
    }

    private void incrementSleepCounter() {
        sleepCount++;
    }

    private void resetSleepCounter() {
        sleepCount = 0;
    }

    private boolean isSleepRequired() {
        return this.sleepCount == scrapingWantedProperties.getJobListSleepCount();
    }

    private void performSleep() {
        try {
            Thread.sleep(scrapingWantedProperties.getSleepTimeMillis());
        } catch (InterruptedException e) {
            log.error("JobListFetchManager.performSleep {}", "Thread sleep 중 Error 발생");
            throw new CrawlerException(CrawlerServerErrorCode.INTERNAL_SERVER_ERROR_THREAD_SLEEP);
        }
    }

    public boolean isDuplicateRequired() {
        return scrapingWantedProperties.getJobDetailDuplicateLimitCount() >= scrapingWantedProperties.getScraping()
            .getJobDetailDuplicateLimitCount();
    }
}
