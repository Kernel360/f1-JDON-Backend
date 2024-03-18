package kernel.jdon.modulebatch.jd.reader.counter;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.config.ScrapingWantedProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class JobListFetchManager {
    private final ScrapingWantedProperties scrapingWantedProperties;
    private int offset = 0;
    private int sleepCount = 0;

    public int getOffset() {
        return this.offset;
    }

    public void incrementOffset() {
        this.offset += scrapingWantedProperties.getMaxFetchJdListOffset();
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
        return this.sleepCount == scrapingWantedProperties.getAllScraping().getSleepCount();
    }

    private void performSleep() {
        try {
            Thread.sleep(scrapingWantedProperties.getSleepTimeMillis());
        } catch (InterruptedException e) {
            log.error("JobListFetchManager.performSleep {}", "Thread sleep 중 Error 발생");
            throw new RuntimeException(e); // todo: 에러코드 정의 필요
        }
    }
}
