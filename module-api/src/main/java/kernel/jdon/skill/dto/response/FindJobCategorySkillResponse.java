package kernel.jdon.skill.dto.response;

import kernel.jdon.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindJobCategorySkillResponse {
	private Long skillId;
	private String keyword;

	public static FindJobCategorySkillResponse of(Skill skill) {
		return FindJobCategorySkillResponse.builder()
			.skillId(skill.getId())
			.keyword(skill.getKeyword())
			.build();
	}
}
