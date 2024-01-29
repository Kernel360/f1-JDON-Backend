package kernel.jdon.crawler.wanted.service.infrastructure;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kernel.jdon.crawler.config.ScrapingWantedConfig;

public class JobListProcessingCounter {
	private final int maxFetchJDListSize;
	private final int maxFetchJDListOffset;
	private int offset = 0;
	private Set<Long> fetchedJobIds = new LinkedHashSet<>();

	public JobListProcessingCounter(ScrapingWantedConfig scrapingWantedConfig) {
		this.maxFetchJDListSize = scrapingWantedConfig.getMaxFetchJdList().getSize();
		this.maxFetchJDListOffset = scrapingWantedConfig.getMaxFetchJdList().getOffset();
	}

	public boolean isBelowSizeLimit() {
		return fetchedJobIds.size() < maxFetchJDListSize;
	}

	public boolean isBelowOffsetLimit(int jobIdListSize) {
		return jobIdListSize < maxFetchJDListOffset;
	}

	public void incrementOffset() {
		offset += maxFetchJDListOffset;
	}

	public int getOffset() {
		return this.offset;
	}

	public Set<Long> getFetchedJobIds() {
		return fetchedJobIds;
	}

	public void addFetchedJobIds(List<Long> jobIdList) {
		fetchedJobIds.addAll(jobIdList);
	}
}
