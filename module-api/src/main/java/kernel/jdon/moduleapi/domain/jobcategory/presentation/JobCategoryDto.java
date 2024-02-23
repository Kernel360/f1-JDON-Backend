package kernel.jdon.moduleapi.domain.jobcategory.presentation;

import java.util.List;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobCategoryDto {

	@Getter
	@Builder
	public static class FindJobGroupListResponse {
		private List<JobCategoryInfo.FindJobGroup> jobGroupList;
	}

	@Getter
	@Builder
	public static class FindJobGroup {
		private Long id;
		private String name;
		private List<JobCategoryInfo.FindJobCategory> jobCategoryList;
	}

	@Getter
	@Builder
	public static class FindJobCategory {
		private Long id;
		private String name;
	}

}
