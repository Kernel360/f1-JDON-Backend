package kernel.jdon.modulecrawler.domain.jobcategory.core;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;

public interface JobCategoryReader {
    JobCategory findByWantedCode(String wantedCode);
}
