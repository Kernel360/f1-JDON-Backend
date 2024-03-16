package kernel.jdon.modulebatch.jd.search;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchJobCategory implements SearchCondition {
    JOB_DEVELOPER("518", "개발");

    public final static String SEARCH_KEY = "job_group_id";
    private final String searchValue;
    private final String description;

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSearchValue() {
        return this.searchValue;
    }
}
