package kernel.jdon.moduleapi.domain.jd.presentation;

import java.util.List;

import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdDto {

	@Getter
	@Builder
	public static class FindWantedJdResponse {
		private final Long id;
		private final String title;
		private final String company;
		private final String imageUrl;
		private final String jdUrl;
		private final String requirements;
		private final String mainTasks;
		private final String intro;
		private final String benefits;
		private final String preferredPoints;
		private final String jobCategoryName;
		private final int reviewCount;
		private final List<FindSkill> skillList;
	}

	@Getter
	@Builder
	public static class FindSkill {
		private final Long id;
		private final String keyword;
	}

	@Getter
	@Builder
	public static class FindWantedJdListResponse {
		private final List<FindWantedJd> content;
		private final CustomPageInfo pageInfo;
	}

	@Getter
	@Builder
	public static class FindWantedJd {
		private final Long id;
		private final String title;
		private final String company;
		private final String imageUrl;
		private final String jobCategoryName;
	}
}
