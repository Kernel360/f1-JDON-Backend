package kernel.jdon.modulecrawler.legacy.inflearn.search;

import kernel.jdon.modulecrawler.legacy.search.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CourseSearchSort implements SearchCondition {

    SORT_RECOMMMENDED("seq", "추천순"),
    SORT_POPULARITY("popular", "인기순"),
    SORT_LATEST("recent", "최신순"),
    SORT_RATING("rating", "별점순"),
    SORT_LIKE("famous", "좋아요순");

    public final static String SEARCH_KEY = "order";
    private final String searchValue;
    private final String description;

    @Override
    public String getSearchValue() {
        return this.searchValue;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
