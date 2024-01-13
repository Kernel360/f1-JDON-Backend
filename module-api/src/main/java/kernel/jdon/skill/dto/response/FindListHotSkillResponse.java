package kernel.jdon.skill.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindListHotSkillResponse {
	private List<FindHotSkillResponse> skillList;
}
