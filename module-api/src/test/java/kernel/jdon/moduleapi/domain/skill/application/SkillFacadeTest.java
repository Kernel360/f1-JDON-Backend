package kernel.jdon.moduleapi.domain.skill.application;

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
}