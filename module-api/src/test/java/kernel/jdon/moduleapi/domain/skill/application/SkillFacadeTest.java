package kernel.jdon.moduleapi.domain.skill.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
    @DisplayName("1: getHotSkillList 메서드가 존재하는 HotSkill 목록 데이터를 응답한다.")
    void givenValidHotSkillList_whenFindList_thenReturnCorrectHotSkillList() {
        // given
        final var mockHotSkillListInfo = mock(SkillInfo.FindHotSkillListResponse.class);
        given(skillService.getHotSkillList()).willReturn(mockHotSkillListInfo);

        // when
        final var response = skillFacade.getHotSkillList();

        // then
        assertThat(response).isEqualTo(mockHotSkillListInfo);
        then(skillService).should(times(1)).getHotSkillList();
    }

    @Test
    @DisplayName("2: getMemberSkillList 메서드가 존재하는 memberSkill 목록 데이터를 응답한다.")
    void givenValidMemberSkillList_whenFindList_thenReturnCorrectHotSkillList() {
        // given
        var memberId = 1L;
        var mockMemberSkillListInfo = mock(SkillInfo.FindMemberSkillListResponse.class);
        given(skillService.getMemberSkillList(memberId)).willReturn(mockMemberSkillListInfo);

        // when
        var response = skillFacade.getMemberSkillList(memberId);

        // then
        assertThat(response).isEqualTo(mockMemberSkillListInfo);
        then(skillService).should(times(1)).getMemberSkillList(memberId);
    }

    @Test
    @DisplayName("3: 올바른 직군 ID가 주어졌을 때, getJobCategorySkillList 메서드가 직군별 기술스택 목록 데이터를 응답한다.")
    void givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList() throws Exception {
        //given
        final var jobCategoryId = 1L;
        final var mockJobCategorySkillListInfo = mock(SkillInfo.FindJobCategorySkillListResponse.class);
        given(skillService.getJobCategorySkillList(jobCategoryId)).willReturn(mockJobCategorySkillListInfo);

        //when
        final var response = skillFacade.getJobCategorySkillList(jobCategoryId);

        //then
        assertThat(response).isEqualTo(mockJobCategorySkillListInfo);
        then(skillService).should(times(1)).getJobCategorySkillList(jobCategoryId);
    }

    @Test
    @DisplayName("4: keyword, memberId가 주어졌을 때, getDataListBySkill 메서드가 기술스택 기반 강의, JD 목록 데이터를 응답한다.")
    void givenValidKeyword_whenFindList_thenReturnCorrectDataListBySkill() throws Exception {
        //given
        final String keyword = "AWS";
        final var memberId = 1L;
        final var mockDataListBySkillInfo = mock(SkillInfo.FindDataListBySkillResponse.class);
        given(skillService.getDataListBySkill(keyword, memberId)).willReturn(mockDataListBySkillInfo);

        //when
        var response = skillFacade.getDataListBySkill(keyword, memberId);

        //then
        assertThat(response).isEqualTo(mockDataListBySkillInfo);
        then(skillService).should(times(1)).getDataListBySkill(keyword, memberId);
    }
}