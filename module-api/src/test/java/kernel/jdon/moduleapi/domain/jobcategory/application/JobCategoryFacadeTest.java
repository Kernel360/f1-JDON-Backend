package kernel.jdon.moduleapi.domain.jobcategory.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("JobCategory Facade 테스트")
class JobCategoryFacadeTest {
    @InjectMocks
    private JobCategoryFacade jobCategoryFacade;
    @Mock
    private JobCategoryService jobCategoryService;

    @Test
    @DisplayName("1: getJobGroupList 메서드가 존재하는 직군별 직무 목록 데이터를 응답한다.")
    void givenNone_whenFindList_thenReturnCorrectJobGroupList() throws Exception {
        //given
        var jobGroupListResponse = getMockJobGroupList();

        //when
        when(jobCategoryService.getJobGroupList()).thenReturn(jobGroupListResponse);
        var response = jobCategoryFacade.getJobGroupList();

        //then
        assertThat(response.getJobGroupList()).hasSize(2);
        assertThat(response.getJobGroupList().get(0).getJobCategoryList()).hasSize(3);
        verify(jobCategoryService, times(1)).getJobGroupList();
    }

    private JobCategoryInfo.FindJobGroupListResponse getMockJobGroupList() {
        var jobCategoryList = List.of(
            mock(JobCategoryInfo.FindJobCategory.class),
            mock(JobCategoryInfo.FindJobCategory.class),
            mock(JobCategoryInfo.FindJobCategory.class));
        var jobGroupList = List.of(
            JobCategoryInfo.FindJobGroup.builder().id(1L).name("GroupName1").jobCategoryList(jobCategoryList).build(),
            JobCategoryInfo.FindJobGroup.builder().id(2L).name("GroupName2").jobCategoryList(jobCategoryList).build());
        var jobGroupListResponse = new JobCategoryInfo.FindJobGroupListResponse(jobGroupList);
        return jobGroupListResponse;
    }
}