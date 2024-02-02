package kernel.jdon.crawler.inflearn.service.infrastructure;

import java.util.Random;

import kernel.jdon.crawler.config.ScrapingInflearnConfig;
import lombok.Getter;

@Getter
public class DynamicSleepTimeManager {
	private int dynamicSleepTime;
	private final int minInitial;
	private final int maxInitial;
	private final int max;
	private final int increment;
	private final Random random = new Random();

	public DynamicSleepTimeManager(ScrapingInflearnConfig scrapingInflearnConfig) {
		this.minInitial = scrapingInflearnConfig.getSleep().getMinInitial();
		this.maxInitial = scrapingInflearnConfig.getSleep().getMaxInitial();
		this.max = scrapingInflearnConfig.getSleep().getMax();
		this.increment = scrapingInflearnConfig.getSleep().getIncrement();
		this.dynamicSleepTime = this.minInitial;
	}

	public void adjustSleepTime(boolean requestSuccess) {
		if (requestSuccess) {
			dynamicSleepTime = minInitial + random.nextInt(maxInitial - minInitial + 1);
		} else {
			dynamicSleepTime = Math.min(dynamicSleepTime + increment, max);
		}
	}
}
