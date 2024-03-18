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
public class JobDetailFetchManager {
    private final ScrapingWantedProperties scrapingWantedProperties;
    private int sleepCount = 0;

    public void incrementSleepCounter() {
        sleepCount++;
        if (isSleepRequired()) {
            performSleep();
            resetSleepCounter();
        }
    }

    private void resetSleepCounter() {
        sleepCount = 0;
    }

    private boolean isSleepRequired() {
        return this.sleepCount == scrapingWantedProperties.getAllScraping().getJobDetailSleepCount();
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
