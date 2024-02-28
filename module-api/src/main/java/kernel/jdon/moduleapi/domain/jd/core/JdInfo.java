package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdInfo {

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
	@EqualsAndHashCode
	public static class FindSkill {
		private final Long id;
		private final String keyword;

		public FindSkill(Skill skill) {
			this.id = skill.getId();
			this.keyword = skill.getKeyword();
		}
	}

	@Getter
	@AllArgsConstructor
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
