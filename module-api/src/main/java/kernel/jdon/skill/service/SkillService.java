package kernel.jdon.skill.service;

import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import kernel.jdon.skill.dto.object.FindWantedJdDto;
import kernel.jdon.skill.dto.response.FindJobCategorySkillResponse;
import kernel.jdon.skill.dto.object.FindLectureDto;
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

	public FindListDataBySkillResponse findDataBySkillList(String keyword) {
		keyword = hasText(keyword) ? keyword
								   : skillRepository.findHotSkillList().get(0).getKeyword();
		List<FindWantedJdDto> findWantedJdList = skillRepository.findWantedJdListBySkill(keyword);
		List<FindLectureDto> findLectureList = skillRepository.findInflearnLectureListBySkill(keyword);

		return FindListDataBySkillResponse.of(keyword, findLectureList, findWantedJdList);
	}
}
