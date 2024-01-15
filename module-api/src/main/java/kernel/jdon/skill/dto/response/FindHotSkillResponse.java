package kernel.jdon.skill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindHotSkillResponse {
	private String keyword;

	public static FindHotSkillResponse of(String keyword) {
		return new FindHotSkillResponse(keyword);
	}
}
