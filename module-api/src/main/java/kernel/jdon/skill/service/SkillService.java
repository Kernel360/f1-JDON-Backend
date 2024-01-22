package kernel.jdon.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import kernel.jdon.skill.dto.response.FindJobCategorySkillResponse;
import kernel.jdon.skill.dto.response.FindListHotSkillResponse;
import kernel.jdon.skill.dto.response.FindListJobCategorySkillResponse;
import kernel.jdon.skill.dto.response.FindListMemberSkillResponse;
import kernel.jdon.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {
	private final SkillRepository skillRepository;

	public FindListHotSkillResponse findHotSkillList() {
		List<FindHotSkillDto> findHotSkillList = skillRepository.findHotSkillList();

		return new FindListHotSkillResponse(findHotSkillList);
	}

	public FindListMemberSkillResponse findMemberSkillList(Long memberId) {
		List<FindMemberSkillDto> findMemberSkillList = skillRepository.findMemberSkillList(memberId);

		return new FindListMemberSkillResponse(findMemberSkillList);
	}

	public FindListJobCategorySkillResponse findJobCategorySkillList(Long jobCategoryId) {
		List<FindJobCategorySkillResponse> findJobCategorySkillList = skillRepository.findAllByJobCategoryId(jobCategoryId)
			.stream()
			.map(FindJobCategorySkillResponse::of)
			.toList();


		return new FindListJobCategorySkillResponse(findJobCategorySkillList);
	}
}
