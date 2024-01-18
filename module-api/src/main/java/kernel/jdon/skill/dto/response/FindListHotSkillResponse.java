package kernel.jdon.skill.dto.response;

import java.util.List;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindListHotSkillResponse {
	private List<FindHotSkillDto> skillList;
}
