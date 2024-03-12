package kernel.jdon.modulecrawler.wanted.service.infrastructure;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kernel.jdon.modulecrawler.config.ScrapingWantedProperties;

public class JobListProcessingCounter {
    private final int maxFetchJDListSize;
    private final int maxFetchJDListOffset;
    private int offset = 0;
    private Set<Long> fetchedJobIds = new LinkedHashSet<>();

    public JobListProcessingCounter(final ScrapingWantedProperties scrapingWantedProperties) {
        this.maxFetchJDListSize = scrapingWantedProperties.getMaxFetchJdListSize();
        this.maxFetchJDListOffset = scrapingWantedProperties.getMaxFetchJdListOffset();
    }

    public boolean isMaxFetchSizeLimit() {
        return fetchedJobIds.size() < maxFetchJDListSize;
    }

    public boolean isMaxFetchOffsetLimit(final int jobIdListSize) {
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

    public void addFetchedJobIds(final List<Long> jobIdList) {
        fetchedJobIds.addAll(jobIdList);
    }
}
