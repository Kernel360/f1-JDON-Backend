package kernel.jdon.moduleapi.domain.jobcategory.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCategoryFacade {
	private final JobCategoryService jobCategoryService;

	public JobCategoryInfo.FindJobGroupListResponse getJobGroupList() {
		return jobCategoryService.getJobGroupList();
	}
}
