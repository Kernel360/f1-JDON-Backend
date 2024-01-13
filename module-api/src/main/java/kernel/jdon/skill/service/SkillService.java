package kernel.jdon.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel.jdon.skill.dto.response.FindHotSkillResponse;
import kernel.jdon.skill.dto.response.FindListHotSkillResponse;
import kernel.jdon.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {
	private final SkillRepository skillRepository;

	public FindListHotSkillResponse findHotSkillList() {
		List<FindHotSkillResponse> findHotSkillList = skillRepository.findHotSkillList().stream()
			.map(FindHotSkillResponse::of)
			.toList();

		return new FindListHotSkillResponse(findHotSkillList);
	}
}
