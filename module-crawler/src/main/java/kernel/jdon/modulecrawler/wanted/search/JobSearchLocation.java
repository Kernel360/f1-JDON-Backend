package kernel.jdon.modulecrawler.wanted.search;

import kernel.jdon.modulecrawler.common.search.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchLocation implements SearchCondition {
    LOCATIONS_ALL("all", "전체");

    public final static String SEARCH_KEY = "locations";
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
