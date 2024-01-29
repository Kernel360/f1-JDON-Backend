package kernel.jdon.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scraping.wanted")
public class ScrapingWantedConfig {
	private final MaxFetchJdListConfig maxFetchJdList;
	private final Sleep sleep;
	private final Url url;

	@Getter
	@RequiredArgsConstructor
	public static class MaxFetchJdListConfig {
		private final int size;
		private final int offset;
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
}
