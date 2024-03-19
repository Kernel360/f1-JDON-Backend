package kernel.jdon.modulebatch.job.jd.reader.condition;

import kernel.jdon.modulebatch.global.condition.SearchCondition;
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
