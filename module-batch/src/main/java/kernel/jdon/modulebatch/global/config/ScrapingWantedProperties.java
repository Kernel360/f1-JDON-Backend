package kernel.jdon.modulebatch.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scraping.wanted")
public class ScrapingWantedProperties {
    private final Sleep sleep;
    private final Url url;
    private final Scraping scraping;

    public String getDetailUrl() {
        return this.url.detail;
    }

    public String getApiDetailUrl() {
        return this.url.api.detail;
    }

    public String getApiListUrl() {
        return this.url.api.list;
    }

    public int getJobListOffset() {
        return this.scraping.jobListOffset;
    }

    public int getJobListLimit() {
        return this.scraping.jobListLimit;
    }

    public int getJobDetailDuplicateLimitCount() {
        return this.scraping.part.jobDetailDuplicateLimitCount;
    }

    public int getSleepTimeMillis() {
        return this.sleep.timeMillis;
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
    public static class Scraping {
        private final int jobListLimit;
        private final int jobListOffset;
        private final AllScraping all;
        private final PartScraping part;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllScraping {
        private final int jobListSleepCount;
        private final int jobDetailSleepCount;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PartScraping {
        private final int jobDetailDuplicateLimitCount;
    }
}
