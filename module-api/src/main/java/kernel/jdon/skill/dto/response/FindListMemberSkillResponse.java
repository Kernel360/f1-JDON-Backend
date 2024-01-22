package kernel.jdon.skill.dto.response;

import java.util.List;

import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindListMemberSkillResponse {
	private List<FindMemberSkillDto> skillList;
}
