package kernel.jdon.modulecrawler.legacy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scraping.inflearn")
public class ScrapingInflearnProperties {
    private final String detailUrlPrefix;
    private final String url;
    private final int maxCoursesPerKeyword;
    private final int maxCoursesPerPage;
    private final Sleep sleep;

    public int getMinInitialSleepTime() {
        return this.sleep.minInitial;
    }

    public int getMaxInitialSleepTime() {
        return this.sleep.maxInitial;
    }

    public int getMaxSleepTime() {
        return this.sleep.max;
    }

    public int getSleepTimeIncrement() {
        return this.sleep.increment;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Sleep {
        private final int minInitial;
        private final int maxInitial;
        private final int max;
        private final int increment;
    }
}
