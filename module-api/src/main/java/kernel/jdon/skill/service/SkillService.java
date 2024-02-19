package kernel.jdon.skill.service;

import org.springframework.stereotype.Service;

import kernel.jdon.skill.dto.response.FindListDataBySkillResponse;
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
		// List<FindHotSkillDto> findHotSkillList = skillRepository.findHotSkillList();
		//
		// return new FindListHotSkillResponse(findHotSkillList);
		return null;
	}

	public FindListMemberSkillResponse findMemberSkillList(Long memberId) {
		// List<FindMemberSkillDto> findMemberSkillList = skillRepository.findMemberSkillList(memberId);
		//
		// return new FindListMemberSkillResponse(findMemberSkillList);
		return null;
	}

	public FindListJobCategorySkillResponse findJobCategorySkillList(Long jobCategoryId) {
		// List<FindJobCategorySkillResponse> findJobCategorySkillList = skillRepository.findAllByJobCategoryId(jobCategoryId)
		// 	.stream()
		// 	.map(FindJobCategorySkillResponse::of)
		// 	.toList();
		//
		// return new FindListJobCategorySkillResponse(findJobCategorySkillList);
		return null;
	}

	public FindListDataBySkillResponse findDataBySkillList(String keyword, Long userId) {
		// keyword = hasText(keyword) ? keyword
		// 						   : skillRepository.findHotSkillList().get(0).getKeyword();
		// List<FindWantedJdDto> findWantedJdList = skillRepository.findWantedJdListBySkill(keyword);
		// List<FindLectureDto> findLectureList = skillRepository.findInflearnLectureListBySkill(keyword, userId);
		//
		// return FindListDataBySkillResponse.of(keyword, findLectureList, findWantedJdList);
		return null;
	}
}
