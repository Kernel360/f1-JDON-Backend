package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdInfo {

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
		private int reviewCount;
		private List<FindSkill> skillList;
	}

	@Getter
	public static class FindSkill {
		private Long id;
		private String keyword;

		public FindSkill(Skill skill) {
			this.id = skill.getId();
			this.keyword = skill.getKeyword();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class FindWantedJdListResponse {
		private List<FindWantedJd> content;
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
