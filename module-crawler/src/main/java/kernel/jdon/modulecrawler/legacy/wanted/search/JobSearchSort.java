package kernel.jdon.modulecrawler.legacy.wanted.search;

import kernel.jdon.modulecrawler.legacy.search.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchSort implements SearchCondition {
    SORT_LATEST("job.latest_order", "최신순"),
    SORT_POPULARITY("job.popularity_order", "인기순");

    public static final String SEARCH_KEY = "job_sort";
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
