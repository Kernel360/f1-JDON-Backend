package kernel.jdon.modulecrawler.legacy.wanted.service.infrastructure;

import kernel.jdon.modulecrawler.legacy.config.ScrapingWantedProperties;

public class JobDetailProcessingCounter {
    private final int thresholdCount;
    private final int failLimitCount;
    private int sleepCount = 0;
    private int consecutiveFailCount = 0;

    public JobDetailProcessingCounter(ScrapingWantedProperties scrapingWantedProperties) {
        this.thresholdCount = scrapingWantedProperties.getSleepThresholdCount();
        this.failLimitCount = scrapingWantedProperties.getLimitFailCount();
    }

    public void incrementSleepCounter() {
        sleepCount++;
    }

    public void resetSleepCounter() {
        sleepCount = 0;
    }

    public void incrementFailCount() {
        consecutiveFailCount++;
    }

    public void resetFailCount() {
        consecutiveFailCount = 0;
    }

    public boolean isSleepRequired() {
        return sleepCount == thresholdCount;
    }

    public boolean isBreakRequired() {
        return consecutiveFailCount == failLimitCount;
    }
}

