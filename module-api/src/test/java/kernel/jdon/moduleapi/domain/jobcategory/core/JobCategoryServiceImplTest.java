package kernel.jdon.moduleapi.domain.jobcategory.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.util.JsonFileReader;

@ExtendWith(MockitoExtension.class)
@DisplayName("JobCategory Service Impl 테스트")
class JobCategoryServiceImplTest {
	@InjectMocks
	private JobCategoryServiceImpl jobCategoryServiceImpl;
	@Mock
	private JobCategoryReader jobCategoryReader;

	@Test
	@DisplayName("1: getJobGroupList 메서드가 존재하는 직군별 직무 목록 데이터를 응답한다.")
	void whenFindList_thenReturnCorrectJobGroupList() throws Exception {
		//given
		String filePath1 = "giventest/jobcategory/serviceimpl/1_jobcategory_1.json";
		List<JobCategory> parentJobCategoryList = getMockJobCategories(filePath1);

		String filePath2 = "giventest/jobcategory/serviceimpl/1_jobcategory_2.json";
		List<JobCategory> subJobCategoryList = getMockJobCategories(filePath2);

		//when
		when(jobCategoryReader.findByParentIdIsNull()).thenReturn(parentJobCategoryList);
		when(jobCategoryReader.findByParentId(parentJobCategoryList.get(0).getId())).thenReturn(subJobCategoryList);
		var response = jobCategoryServiceImpl.getJobGroupList();

		//then
		assertThat(response.getJobGroupList()).hasSize(1);
		assertThat(response.getJobGroupList().get(0).getName()).isEqualTo("개발");
		assertThat(response.getJobGroupList().get(0).getJobCategoryList()).hasSize(1);
		assertThat(response.getJobGroupList().get(0).getJobCategoryList().get(0).getName())
			.isEqualTo("서버개발자");
		verify(jobCategoryReader, times(1)).findByParentIdIsNull();
		verify(jobCategoryReader, times(1)).findByParentId(parentJobCategoryList.get(0).getId());
	}

	private List<JobCategory> getMockJobCategories(String filePath) throws IOException {
		JobCategory parentJobCategory = JsonFileReader.readJsonFileToObject(filePath, JobCategory.class);
		List<JobCategory> parentJobCategoryList = Collections.singletonList(parentJobCategory);
		return parentJobCategoryList;
	}

}