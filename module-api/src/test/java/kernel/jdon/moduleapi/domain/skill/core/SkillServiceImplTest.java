package kernel.jdon.moduleapi.domain.skill.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.core.wantedjd.WantedJdSkillReader;

@DisplayName("Skill Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {
	@Mock
	private SkillReader skillReader;
	@Mock
	private JobCategoryReader jobCategoryReader;
	@Mock
	private WantedJdSkillReader wantedJdSkillReader;
	@Mock
	private InflearnJdSkillReader inflearnJdSkillReader;
	@InjectMocks
	private SkillServiceImpl skillServiceImpl;

	@Test
	@DisplayName("getHotSkillList 메서드가 존재하는 HotSkill 개수 만큼 데이터를 응답한다.")
	void givenValidHotSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var hotSkillList = Collections.singletonList(new SkillInfo.FindHotSkill(1L, "hotSkill_keyword"));

		// when
		when(skillReader.findHotSkillList()).thenReturn(hotSkillList);
		var response = skillServiceImpl.getHotSkillList();

		// then
		assertThat(response.getSkillList()).hasSize(1);
		verify(skillReader, times(1)).findHotSkillList();
	}

	@Test
	@DisplayName("getMemberSkillList 메서드가 존재하는 memberSkill 개수 만큼 데이터를 응답한다.")
	void givenValidMemberSkillList_whenFindList_thenReturnCorrectHotSkillList() {
		// given
		var memberSkillList = Arrays.asList(
			new SkillInfo.FindMemberSkill(1L, "member_skill_keyword_1"),
			new SkillInfo.FindMemberSkill(2L, "member_skill_keyword_2"),
			new SkillInfo.FindMemberSkill(3L, "member_skill_keyword_3"));
		Long memberId = 1L;

		// when
		when(skillReader.findMemberSkillList(memberId)).thenReturn(memberSkillList);
		var response = skillServiceImpl.getMemberSkillList(memberId);

		// then
		assertThat(response.getSkillList()).hasSize(3);
		verify(skillReader, times(1)).findMemberSkillList(memberId);
	}

	@Test
	@DisplayName("올바른 직군 ID가 주어졌을 때 getJobCategorySkillList 메서드가 직군별 기술스택에서 '기타' 키워드를 제외한 데이터 개수 만큼 데이터를 응답한다.")
	void givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList() throws Exception {
		//given
		String filePath = "giventest/skill/serviceimpl/givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList.json";
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = new ClassPathResource(filePath).getInputStream();
		JobCategory jobCategory = objectMapper.readValue(inputStream, JobCategory.class);
		final Long jobCategoryId = 1L;

		//when
		when(jobCategoryReader.findById(jobCategoryId)).thenReturn(jobCategory);
		var response = skillServiceImpl.getJobCategorySkillList(jobCategoryId);

		//then
		assertThat(response.getSkillList()).hasSize(2);
		verify(jobCategoryReader, times(1)).findById(jobCategoryId);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {""})
	@DisplayName("keyword가 존재하지 않을 때 getDataListBySkill 메서드가 인기있는 keyword 기반으로 데이터를 응답한다.")
	void givenEmptyKeyword_whenFindList_thenReturnCorrectDataListByHotSkill(final String keyword) throws
		Exception {
		//given
		final String hotSkillKeyword = "hotSkill_keyword";
		final var hotSkillList = Collections.singletonList(new SkillInfo.FindHotSkill(1L, hotSkillKeyword));
		final Long memberId = 1L;

		//when
		when(wantedJdSkillReader.findWantedJdListBySkill(hotSkillKeyword)).thenReturn(null);
		when(inflearnJdSkillReader.findInflearnLectureListBySkill(hotSkillKeyword, memberId)).thenReturn(null);
		when(skillReader.findHotSkillList()).thenReturn(hotSkillList);
		var response = skillServiceImpl.getDataListBySkill(keyword, memberId);

		//then
		assertThat(response.getKeyword()).isEqualTo(hotSkillKeyword);
		verify(wantedJdSkillReader, times(1)).findWantedJdListBySkill(hotSkillKeyword);
		verify(inflearnJdSkillReader, times(1)).findInflearnLectureListBySkill(hotSkillKeyword, memberId);
	}
}
