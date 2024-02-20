package kernel.jdon.moduleapi.domain.skill.infrastructure;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillReaderInfo {

	@Getter
	public static class FindHotSkill {
		private Long id;
		private String keyword;

		@QueryProjection
		public FindHotSkill(Long id, String keyword) {
			this.id = id;
			this.keyword = keyword;
		}
	}

	@Getter
	public static class FindMemberSkill {
		private Long skillId;
		private String keyword;

		@QueryProjection
		public FindMemberSkill(Long skillId, String keyword) {
			this.skillId = skillId;
			this.keyword = keyword;
		}
	}
}
