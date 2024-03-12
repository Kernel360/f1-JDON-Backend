package kernel.jdon.moduleapi.domain.jobcategory.core;

import java.util.List;
import java.util.Map;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobCategoryInfo {

    @Getter
    @AllArgsConstructor
    public static class FindJobGroupListResponse {
        private final List<FindJobGroup> jobGroupList;

        public FindJobGroupListResponse(final List<JobCategory> jobGroupList,
            final Map<Long, List<JobCategory>> jobCategoryMap) {
            this.jobGroupList = jobGroupList.stream()
                .map(jobGroup -> JobCategoryInfo.FindJobGroup.of(jobGroup, jobCategoryMap.get(jobGroup.getId())))
                .toList();
        }
    }

    @Getter
    @Builder
    public static class FindJobGroup {
        private final Long id;
        private final String name;
        private final List<FindJobCategory> jobCategoryList;

        public static JobCategoryInfo.FindJobGroup of(final JobCategory jobCategory,
            final List<JobCategory> jobCategoryList) {
            final List<JobCategoryInfo.FindJobCategory> list = jobCategoryList.stream()
                .map(JobCategoryInfo.FindJobCategory::of)
                .toList();

            return JobCategoryInfo.FindJobGroup.builder()
                .id(jobCategory.getId())
                .name(jobCategory.getName())
                .jobCategoryList(list)
                .build();
        }
    }

    @Getter
    @Builder
    public static class FindJobCategory {
        private final Long id;
        private final String name;

        public static JobCategoryInfo.FindJobCategory of(final JobCategory jobCategory) {
            return JobCategoryInfo.FindJobCategory.builder()
                .id(jobCategory.getId())
                .name(jobCategory.getName())
                .build();
        }
    }
}
