package kernel.jdon.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scraping.inflearn")
public class ScrapingInflearnConfig {
	private final String url;
	private final int maxCoursesPerKeyword;
	private final int maxCoursesPerPage;
	private final Sleep sleep;

	@Getter
	@RequiredArgsConstructor
	public static class Sleep {
		private final int minInitial;
		private final int maxInitial;
		private final int max;
		private final int increment;
	}
}
