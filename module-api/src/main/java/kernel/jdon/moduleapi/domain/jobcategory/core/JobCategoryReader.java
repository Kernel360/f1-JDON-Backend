package kernel.jdon.moduleapi.domain.jobcategory.core;

import java.util.List;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;

public interface JobCategoryReader {
    JobCategory findById(Long jobCategoryId);

    List<JobCategory> findByParentIdIsNull();

    List<JobCategory> findByParentId(Long parentId);
}
