package kernel.jdon.crawler.wanted.search;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchJobPosition implements JobSearchCondition {
	JOB_POSITION_FRONTEND("669", "프론트엔드 개발자"),
	JOB_POSITION_SERVER("872", "서버 개발자");

	public final static String SEARCH_KEY = "job_ids";
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
