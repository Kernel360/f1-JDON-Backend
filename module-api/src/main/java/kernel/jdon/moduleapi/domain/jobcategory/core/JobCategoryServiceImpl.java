package kernel.jdon.moduleapi.domain.jobcategory.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {
	private final JobCategoryReader jobCategoryReader;

	@Override
	public JobCategoryInfo.FindJobGroupListResponse getJobGroupList() {
		final List<JobCategory> findJobGroupList = jobCategoryReader.findByParentIdIsNull();
		final Map<Long, List<JobCategory>> findGroupedCategoryList = findJobGroupList.stream()
			.collect(Collectors.toMap(JobCategory::getId,
				jobGroup -> jobCategoryReader.findByParentId(jobGroup.getId())));

		return new JobCategoryInfo.FindJobGroupListResponse(findJobGroupList, findGroupedCategoryList);
	}
}
