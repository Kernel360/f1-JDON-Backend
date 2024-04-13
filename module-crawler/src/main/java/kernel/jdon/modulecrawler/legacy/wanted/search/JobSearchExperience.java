package kernel.jdon.modulecrawler.legacy.wanted.search;

import kernel.jdon.modulecrawler.legacy.search.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchExperience implements SearchCondition {
    EXPERIENCE_ALL("-1", "전체");

    public final static String SEARCH_KEY = "years";
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
