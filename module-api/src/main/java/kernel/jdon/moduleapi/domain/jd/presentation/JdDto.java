package kernel.jdon.moduleapi.domain.jd.presentation;

import java.util.List;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
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
		private Long id;
		private String title;
		private String company;
		private String imageUrl;
		private String jdUrl;
		private String requirements;
		private String mainTasks;
		private String intro;
		private String benefits;
		private String preferredPoints;
		private String jobCategoryName;
		private List<FindSkill> skillList;
	}

	@Getter
	@Builder
	public static class FindSkill {
		private Long id;
		private String keyword;
	}

	@Getter
	@Builder
	public static class FindWantedJdListResponse {
		private List<JdInfo.FindWantedJd> content;
		private CustomPageInfo pageInfo;
	}

	@Getter
	@Builder
	public static class FindWantedJd {
		private Long id;
		private String title;
		private String company;
		private String imageUrl;
		private String jobCategoryName;
	}
}
