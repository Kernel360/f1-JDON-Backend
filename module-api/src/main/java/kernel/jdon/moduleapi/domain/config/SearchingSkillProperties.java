package kernel.jdon.moduleapi.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "api.search")
public class SearchingSkillProperties {
	private final int hotSkillKeywordCount;
	private final int wantedJdCount;
	private final int inflearnLectureCount;
}
