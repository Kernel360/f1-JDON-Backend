package kernel.jdon.moduleapi.domain.skill.presentation;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillDto {

	@Getter
	@Builder
	public static class FindHotSkillListResponse {
		private List<FindHotSkill> skillList;
	}

	@Getter
	@Builder
	public static class FindHotSkill {
		private Long id;
		private String keyword;
	}

	@Getter
	@Builder
	public static class FindMemberSkillListResponse {
		private List<FindMemberSkill> skillList;
	}

	@Getter
	@Builder
	public static class FindMemberSkill {
		private Long id;
		private String keyword;
	}

	@Getter
	@Builder
	public static class FindJobCategorySkillListResponse {
		private List<FindJobCategorySkill> skillList;
	}

	@Getter
	@Builder
	public static class FindJobCategorySkill {
		private Long skillId;
		private String keyword;
	}

	@Getter
	@Builder
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
	@Builder
	public static class FindJd {
		private String company;
		private String title;
		private String imageUrl;
		private String jdUrl;
	}

	@Getter
	@Builder
	public static class FindDataListBySkillResponse {
		private String keyword;
		private List<FindLecture> lectureList;
		private List<FindJd> jdList;
	}
}
