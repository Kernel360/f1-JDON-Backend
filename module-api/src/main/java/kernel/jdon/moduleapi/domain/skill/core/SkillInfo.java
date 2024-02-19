package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillInfo {

	@Getter
	@Builder
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
	@Builder
	public static class FinMemberSkillListResponse {
		private List<FindMemberSkill> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class FindMemberSkill {
		private Long id;
		private String keyword;
	}

}
