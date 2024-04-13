package kernel.jdon.modulecrawler.domain.jd.core.condition;

import kernel.jdon.modulecrawler.global.condition.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchExperience implements SearchCondition {
    EXPERIENCE_ALL("-1", "전체");

    public static final String SEARCH_KEY = "years";
    private final String description;
    private final String searchValue;

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSearchValue() {
        return this.searchValue;
    }
}
