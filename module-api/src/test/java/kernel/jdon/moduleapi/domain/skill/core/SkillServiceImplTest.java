package kernel.jdon.moduleapi.domain.skill.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.skill.application.SkillReader;

@DisplayName("Skill Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {
	@Mock
	private SkillReader skillReader;
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
		var findMemberSkillList = skillReader.findMemberSkillList(memberId);

		// then
		assertThat(findMemberSkillList).hasSize(3);
		verify(skillReader, times(1)).findMemberSkillList(memberId);
	}
}