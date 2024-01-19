package kernel.jdon.jobcategory.dto.response;

import java.util.List;
import java.util.Map;

import kernel.jdon.jobcategory.domain.JobCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindListJobGroupResponse {
	private List<FindJobGroupResponse> jobGroupList;

	public FindListJobGroupResponse(List<JobCategory> jobGroupList, Map<Long, List<JobCategory>> jobCategoryMap) {
		this.jobGroupList = jobGroupList.stream()
			.map(jobGroup -> FindJobGroupResponse.of(jobGroup, jobCategoryMap.get(jobGroup.getId())))
			.toList();
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class FindJobGroupResponse {
		private Long id;
		private String name;
		private List<FindJobCategoryResponse> jobCategoryList;

		public static FindJobGroupResponse of(JobCategory jobCategory, List<JobCategory> jobCategoryList) {
			List<FindJobCategoryResponse> list = jobCategoryList.stream()
				.map(FindJobCategoryResponse::of)
				.toList();

			return FindJobGroupResponse.builder()
				.id(jobCategory.getId())
				.name(jobCategory.getName())
				.jobCategoryList(list)
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class FindJobCategoryResponse {
		private Long id;
		private String name;

		public static FindJobCategoryResponse of(JobCategory jobCategory) {
			return FindJobCategoryResponse.builder()
				.id(jobCategory.getId())
				.name(jobCategory.getName())
				.build();
		}
	}
}
