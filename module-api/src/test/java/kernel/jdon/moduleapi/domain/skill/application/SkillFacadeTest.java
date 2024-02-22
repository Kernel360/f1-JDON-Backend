package kernel.jdon.moduleapi.domain.skill.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.SkillService;

@DisplayName("Skill Facade 테스트")
@ExtendWith(MockitoExtension.class)
class SkillFacadeTest {
	@Mock
	private SkillService skillService;
	@InjectMocks
	private SkillFacade skillFacade;

	@Test
	@DisplayName("getHotSkillList 메서드가 존재하는 HotSkill 개수 만큼 데이터를 응답한다.")
	void givenValidHotSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var hotSkillList = Collections.singletonList(new SkillInfo.FindHotSkill(1L, "hot_skill_keyword"));
		var hotSkillListResponse = new SkillInfo.FindHotSkillListResponse(hotSkillList);

		// when
		when(skillService.getHotSkillList()).thenReturn(hotSkillListResponse);
		var response = skillFacade.getHotSkillList();

		// then
		assertThat(response.getSkillList()).hasSize(1);
		verify(skillService, times(1)).getHotSkillList();
	}

	@Test
	@DisplayName("getMemberSkillList 메서드가 존재하는 memberSkill 개수 만큼 데이터를 응답한다.")
	void givenValidMemberSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var memberSkillList = Arrays.asList(
			new SkillInfo.FindMemberSkill(1L, "member_skill_keyword_1"),
			new SkillInfo.FindMemberSkill(2L, "member_skill_keyword_2"),
			new SkillInfo.FindMemberSkill(3L, "member_skill_keyword_3"));
		var memberSkillListResponse = new SkillInfo.FindMemberSkillListResponse(memberSkillList);
		var memberId = 1L;

		// when
		when(skillService.getMemberSkillList(memberId)).thenReturn(memberSkillListResponse);
		var response = skillFacade.getMemberSkillList(memberId);

		// then
		assertThat(response.getSkillList()).hasSize(3);
		verify(skillService, times(1)).getMemberSkillList(memberId);
	}

	@Test
	@DisplayName("올바른 직군 ID가 주어졌을 때 getJobCategorySkillList 메서드가 직군별 기술스택 데이터 개수 만큼 데이터를 응답한다.")
	void givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList() throws Exception {
		//given
		Long jobCategoryId = 1L;
		var jobCategorySkillListResponse = new SkillInfo.FindJobCategorySkillListResponse(Arrays.asList(
			new SkillInfo.FindJobCategorySkill(1L, "JAVA"),
			new SkillInfo.FindJobCategorySkill(2L, "GO"),
			new SkillInfo.FindJobCategorySkill(3L, "Python")));

		//when
		when(skillService.getJobCategorySkillList(jobCategoryId)).thenReturn(jobCategorySkillListResponse);
		var response = skillFacade.getJobCategorySkillList(jobCategoryId);

		//then
		assertThat(response.getSkillList()).hasSize(3);
		verify(skillService, times(1)).getJobCategorySkillList(jobCategoryId);
	}

	@Test
	@DisplayName("keyword가 주어졌을 때 getDataListBySkill 메서드가 keyword 기반의 JD 및 강의 데이터 개수 만큼 데이터를 응답한다.")
	void givenValidKeyword_whenFindList_thenReturnCorrectDataListBySkill() throws Exception {
		//given
		final Long memberId = 1L;
		final String keyword = "AWS";
		final int jdCount = 6;
		final int lectureCount = 3;

		String filePath = "giventest/skill/facade/givenValidKeyword_whenFindList_thenReturnCorrectDataListBySkill.json";
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = new ClassPathResource(filePath).getInputStream();
		var dataListBySkillResponse = objectMapper.readValue(inputStream, SkillInfo.FindDataListBySkillResponse.class);

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