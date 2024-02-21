package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillInfo {

	@Getter
	@AllArgsConstructor
	public static class FindHotSkillListResponse {
		private List<FindHotSkill> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class FindHotSkill {
		private Long id;
		private String keyword;
	}

	@Getter
	@AllArgsConstructor
	public static class FindMemberSkillListResponse {
		private List<FindMemberSkill> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class FindMemberSkill {
		private Long id;
		private String keyword;
	}

	@Getter
	@AllArgsConstructor
	public static class FindJobCategorySkillListResponse {
		private List<FindJobCategorySkill> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class FindJobCategorySkill {
		private Long skillId;
		private String keyword;
	}

	@Getter
	@AllArgsConstructor
	public static class FindDataListBySkillResponse {
		private String keyword;
		private List<FindLecture> lectureList;
		private List<FindJd> jdList;
	}

	@Getter
	@AllArgsConstructor
	public static class FindLecture {
		private Long lectureId;
		private String title;
		private String lectureUrl;
		private String imageUrl;
		private String instructor;
		private Long studentCount;
		private Integer price;
		private Boolean isFavorite;
	}

	@Getter
	@AllArgsConstructor
	public static class FindJd {
		private String company;
		private String title;
		private String imageUrl;
		private String jdUrl;
	}
}
