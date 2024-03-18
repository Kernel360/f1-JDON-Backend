package kernel.jdon.modulebatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scraping.wanted")
public class ScrapingWantedProperties {
    private final MaxFetchJdList maxFetchJdList;
    private final Limit limit;
    private final Sleep sleep;
    private final Url url;
    private final AllScraping allScraping;

    public String getDetailUrl() {
        return this.url.detail;
    }

    public String getApiDetailUrl() {
        return this.url.api.detail;
    }

    public String getApiListUrl() {
        return this.url.api.list;
    }

    public int getMaxFetchJdListOffset() {
        return this.maxFetchJdList.offset;
    }

    public int getMaxFetchJdListSize() {
        return this.maxFetchJdList.size;
    }

    public int getSleepTimeMillis() {
        return this.sleep.timeMillis;
    }

    public int getSleepThresholdCount() {
        return this.sleep.thresholdCount;
    }

    public int getLimitFailCount() {
        return this.limit.failCount;
    }

    @Getter
    @RequiredArgsConstructor
    public static class MaxFetchJdList {
        private final int size;
        private final int offset;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Limit {
        private final int failCount;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Sleep {
        private final int thresholdCount;
        private final int timeMillis;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Url {
        private final String detail;
        private final Api api;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Api {
        private final String detail;
        private final String list;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllScraping {
        private final int sleepCount;
    }
}
