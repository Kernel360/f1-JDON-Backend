package kernel.jdon.moduleapi.domain.jobcategory.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryService;
import kernel.jdon.util.JsonFileReader;

@ExtendWith(MockitoExtension.class)
@DisplayName("JobCategory Facade 테스트")
class JobCategoryFacadeTest {
	@InjectMocks
	private JobCategoryFacade jobCategoryFacade;
	@Mock
	private JobCategoryService jobCategoryService;

	@Test
	@DisplayName("getJobGroupList 메서드가 존재하는 직군별 직무 목록 데이터를 응답한다.")
	void givenNone_whenFindList_thenReturnCorrectJobGroupList() throws Exception {
		//given
		String filePath = "giventest/jobcategory/facade/givenNone_whenFindList_thenReturnCorrectJobGroupList.json";
		var jobGroupListResponse = JsonFileReader.readJsonFileToObject(filePath,
			JobCategoryInfo.FindJobGroupListResponse.class);

		//when
		when(jobCategoryService.getJobGroupList()).thenReturn(jobGroupListResponse);
		var response = jobCategoryFacade.getJobGroupList();

		//then
		assertThat(response.getJobGroupList()).hasSize(1);
		assertThat(response.getJobGroupList().get(0).getName()).isEqualTo("개발");
		assertThat(response.getJobGroupList().get(0).getJobCategoryList()).hasSize(2);
		assertThat(response.getJobGroupList().get(0).getJobCategoryList().get(0).getName())
			.isEqualTo("서버개발자");
		verify(jobCategoryService, times(1)).getJobGroupList();
	}
}