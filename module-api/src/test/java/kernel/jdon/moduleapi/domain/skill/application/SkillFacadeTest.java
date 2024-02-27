package kernel.jdon.moduleapi.domain.skill.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.SkillService;
import kernel.jdon.util.JsonFileReader;

@DisplayName("Skill Facade 테스트")
@ExtendWith(MockitoExtension.class)
class SkillFacadeTest {
	@Mock
	private SkillService skillService;
	@InjectMocks
	private SkillFacade skillFacade;

	@Test
	@DisplayName("1: getHotSkillList 메서드가 존재하는 HotSkill 개수 만큼 데이터를 응답한다.")
	void givenValidHotSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var hotSkillList = Collections.singletonList(toHotSkill(1L, "hot_skill_keyword"));
		var hotSkillListResponse = toHotSkillList(hotSkillList);

		// when
		when(skillService.getHotSkillList()).thenReturn(hotSkillListResponse);
		var response = skillFacade.getHotSkillList();

		// then
		assertThat(response.getSkillList()).hasSize(1);
		verify(skillService, times(1)).getHotSkillList();
	}

	private SkillInfo.FindHotSkillListResponse toHotSkillList(List<SkillInfo.FindHotSkill> skillList) {
		return SkillInfo.FindHotSkillListResponse.builder().skillList(skillList).build();
	}

	private SkillInfo.FindHotSkill toHotSkill(Long id, String keyword) {
		return SkillInfo.FindHotSkill.builder().id(id).keyword(keyword).build();
	}

	@Test
	@DisplayName("2: getMemberSkillList 메서드가 존재하는 memberSkill 개수 만큼 데이터를 응답한다.")
	void givenValidMemberSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var memberSkillList = Arrays.asList(
			toMemberSkill(1L, "member_skill_keyword_1"),
			toMemberSkill(2L, "member_skill_keyword_2"),
			toMemberSkill(3L, "member_skill_keyword_3"));
		var memberSkillListResponse = toMemberSkillList(memberSkillList);
		var memberId = 1L;

		// when
		when(skillService.getMemberSkillList(memberId)).thenReturn(memberSkillListResponse);
		var response = skillFacade.getMemberSkillList(memberId);

		// then
		assertThat(response.getSkillList()).hasSize(3);
		verify(skillService, times(1)).getMemberSkillList(memberId);
	}

	private SkillInfo.FindMemberSkillListResponse toMemberSkillList(List<SkillInfo.FindMemberSkill> skillList) {
		return SkillInfo.FindMemberSkillListResponse.builder().skillList(skillList).build();
	}

	private SkillInfo.FindMemberSkill toMemberSkill(Long id, String keyword) {
		return SkillInfo.FindMemberSkill.builder().id(id).keyword(keyword).build();
	}

	@Test
	@DisplayName("3: 올바른 직군 ID가 주어졌을 때 getJobCategorySkillList 메서드가 직군별 기술스택 데이터 개수 만큼 데이터를 응답한다.")
	void givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList() throws Exception {
		//given
		Long jobCategoryId = 1L;
		var jobCategorySkillListResponse = toJobCategorySkillList(Arrays.asList(
			toJobCategorySkill(1L, "JAVA"),
			toJobCategorySkill(2L, "GO"),
			toJobCategorySkill(3L, "Python")));

		//when
		when(skillService.getJobCategorySkillList(jobCategoryId)).thenReturn(jobCategorySkillListResponse);
		var response = skillFacade.getJobCategorySkillList(jobCategoryId);

		//then
		assertThat(response.getSkillList()).hasSize(3);
		verify(skillService, times(1)).getJobCategorySkillList(jobCategoryId);
	}

	private SkillInfo.FindJobCategorySkillListResponse toJobCategorySkillList(
		List<SkillInfo.FindJobCategorySkill> skillList) {
		return SkillInfo.FindJobCategorySkillListResponse.builder().skillList(skillList).build();
	}

	private SkillInfo.FindJobCategorySkill toJobCategorySkill(Long id, String keyword) {
		return SkillInfo.FindJobCategorySkill.builder().skillId(id).keyword(keyword).build();
	}

	@Test
	@DisplayName("4: keyword가 주어졌을 때 getDataListBySkill 메서드가 keyword 기반의 JD 및 강의 데이터 개수 만큼 데이터를 응답한다.")
	void givenValidKeyword_whenFindList_thenReturnCorrectDataListBySkill() throws Exception {
		//given
		final Long memberId = 1L;
		final String keyword = "AWS";
		final int jdCount = 6;
		final int lectureCount = 3;

		String filePath = "giventest/skill/facade/4_dataListBySkill_1.json";
		var dataListBySkillResponse = JsonFileReader.readJsonFileToObject(filePath,
			SkillInfo.FindDataListBySkillResponse.class);

		//when
		when(skillService.getDataListBySkill(keyword, memberId)).thenReturn(dataListBySkillResponse);
		var response = skillFacade.getDataListBySkill(keyword, memberId);

		//then
		assertThat(response.getJdList()).hasSize(jdCount);
		assertThat(response.getLectureList()).hasSize(lectureCount);
		assertThat(response.getKeyword()).isEqualTo(keyword);
		verify(skillService, times(1)).getDataListBySkill(keyword, memberId);
	}
}