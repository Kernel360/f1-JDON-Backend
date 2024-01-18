package kernel.jdon.skill.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindHotSkillDto {
	private Long id;
	private String keyword;

	@QueryProjection
	public FindHotSkillDto(Long id, String keyword) {
		this.id = id;
		this.keyword = keyword;
	}
}
