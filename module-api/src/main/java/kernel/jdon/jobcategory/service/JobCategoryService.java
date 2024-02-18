package kernel.jdon.jobcategory.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.jobcategory.dto.response.FindListJobGroupResponse;
import kernel.jdon.jobcategory.repository.JobCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCategoryService {
	private final JobCategoryRepository jobCategoryRepository;

	public FindListJobGroupResponse findJobGroupList() {
		List<JobCategory> findJobGroupList = jobCategoryRepository.findByParentIdIsNull();

		//
		Map<Long, List<JobCategory>> groupedCategoryList = findJobGroupList.stream()
			.collect(Collectors.toMap(JobCategory::getId, jobGroup ->
				jobCategoryRepository.findByParentId(jobGroup.getId())));

		return new FindListJobGroupResponse(findJobGroupList, groupedCategoryList);
	}
}
