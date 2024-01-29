package kernel.jdon.crawler.wanted.service.infrastructure;

import kernel.jdon.crawler.config.ScrapingWantedConfig;

public class JobDetailProcessingCounter {
	private final int thresholdCount;
	private final int failLimitCount;
	private int sleepCount = 0;
	private int consecutiveFailCount = 0;

	public JobDetailProcessingCounter(ScrapingWantedConfig scrapingWantedConfig) {
		this.thresholdCount = scrapingWantedConfig.getSleep().getThresholdCount();
		this.failLimitCount = scrapingWantedConfig.getLimit().getFailCount();
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

