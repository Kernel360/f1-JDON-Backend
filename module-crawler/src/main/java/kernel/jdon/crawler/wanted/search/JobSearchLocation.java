package kernel.jdon.crawler.wanted.search;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchLocation implements JobSearchCondition{
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
