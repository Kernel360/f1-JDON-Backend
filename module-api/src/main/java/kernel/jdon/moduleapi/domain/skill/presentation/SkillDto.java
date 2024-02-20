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
}
