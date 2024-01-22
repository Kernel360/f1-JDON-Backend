package kernel.jdon.skill.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindMemberSkillDto {
	private Long skillId;
	private String keyword;

	@QueryProjection
	public FindMemberSkillDto(Long skillId, String keyword) {
		this.skillId = skillId;
		this.keyword = keyword;
	}
}
