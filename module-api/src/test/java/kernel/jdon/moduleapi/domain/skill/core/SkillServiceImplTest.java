package kernel.jdon.moduleapi.domain.skill.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.core.keyword.SkillKeywordReader;
import kernel.jdon.moduleapi.domain.skill.core.wantedjd.WantedJdSkillReader;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import kernel.jdon.util.JsonFileReader;

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
    @Mock
    private SkillKeywordReader skillKeywordReader;
    @InjectMocks
    private SkillServiceImpl skillServiceImpl;

    @Test
    @DisplayName("1: getHotSkillList 메서드가 존재하는 HotSkill 개수 만큼 데이터를 응답한다.")
    void givenValidHotSkillList_whenFindList_thenReturnCorrectHotSkillList() {
        // given
        final var mockHotSkillList = Collections.singletonList(mock(SkillInfo.FindHotSkill.class));
        given(skillReader.findHotSkillList()).willReturn(mockHotSkillList);

        // when
        final var response = skillServiceImpl.getHotSkillList();

        // then
        assertThat(response.getSkillList()).hasSize(1);
        then(skillReader).should(times(1)).findHotSkillList();
    }

    @Test
    @DisplayName("2: getMemberSkillList 메서드가 존재하는 memberSkill 개수 만큼 데이터를 응답한다.")
    void givenValidMemberSkillList_whenFindList_thenReturnCorrectHotSkillList() {
        // given
        final var memberSkillList = Arrays.asList(
            mock(SkillInfo.FindMemberSkill.class),
            mock(SkillInfo.FindMemberSkill.class),
            mock(SkillInfo.FindMemberSkill.class)
        );
        final var memberId = 1L;
        given(skillReader.findMemberSkillList(memberId)).willReturn(memberSkillList);

        // when
        final var response = skillServiceImpl.getMemberSkillList(memberId);

        // then
        assertThat(response.getSkillList()).hasSize(3);
        then(skillReader).should(times(1)).findMemberSkillList(memberId);
    }

    @Test
    @DisplayName("3: 올바른 직군 ID가 주어졌을 때 getJobCategorySkillList 메서드가 직군별 기술스택에서 '기타' 키워드를 제외한 데이터 개수 만큼 데이터를 응답한다.")
    void givenValidJobCategoryId_whenFindList_thenReturnCorrectJobCategorySkillList() throws Exception {
        //given
        final var filePath = "giventest/skill/serviceimpl/3_jobCategorySkillList.json";
        final var jobCategory = JsonFileReader.readJsonFileToObject(filePath, JobCategory.class);
        final var jobCategoryId = 1L;
        given(jobCategoryReader.findById(jobCategoryId)).willReturn(jobCategory);

        //when
        final var response = skillServiceImpl.getJobCategorySkillList(jobCategoryId);

        //then
        assertThat(response.getSkillList()).hasSize(2);
        then(jobCategoryReader).should(times(1)).findById(jobCategoryId);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    @DisplayName("4: keyword가 존재하지 않을 때 getDataListBySkill 메서드가 인기있는 keyword 기반으로 데이터를 응답한다.")
    void givenEmptyKeyword_whenFindList_thenReturnCorrectDataListByHotSkill(final String keyword) throws
        Exception {
        //given
        final var hotSkillKeyword = "hotSkill_keyword";
        final var hotSkillList = Collections.singletonList(new SkillInfo.FindHotSkill(1L, hotSkillKeyword));
        final var mockSkillKeyword = Collections.singletonList(mock(SkillKeyword.class));
        final var mockOriginSkillKeywordList = Collections.singletonList(hotSkillKeyword);
        final var memberId = 1L;
        given(skillReader.findHotSkillList()).willReturn(hotSkillList);
        given(skillKeywordReader.findAllByRelatedKeywordIgnoreCase(hotSkillKeyword)).willReturn(mockSkillKeyword);
        given(skillReader.findOriginSkillKeywordListBySkillKeywordList(mockSkillKeyword)).willReturn(
            mockOriginSkillKeywordList);
        given(wantedJdSkillReader.findWantedJdListBySkill(mockOriginSkillKeywordList)).willReturn(null);
        given(inflearnJdSkillReader.findInflearnLectureListBySkill(mockOriginSkillKeywordList, memberId)).willReturn(
            null);

        //when
        final var response = skillServiceImpl.getDataListBySkill(keyword, memberId);

        //then
        assertThat(response.getKeyword()).isEqualTo(hotSkillKeyword);
        then(skillReader).should(times(1)).findHotSkillList();
        then(skillKeywordReader).should(times(1)).findAllByRelatedKeywordIgnoreCase(hotSkillKeyword);
        then(skillReader).should(times(1)).findOriginSkillKeywordListBySkillKeywordList(mockSkillKeyword);
        then(wantedJdSkillReader).should(times(1)).findWantedJdListBySkill(mockOriginSkillKeywordList);
        then(inflearnJdSkillReader).should(times(1))
            .findInflearnLectureListBySkill(mockOriginSkillKeywordList, memberId);
    }
}
