package kernel.jdon.crawler.inflearn.service.infrastructure;

import kernel.jdon.crawler.config.ScrapingInflearnConfig;

public class LastPageDiscriminator {
	private boolean isLastPage = false;
	private final int maxCoursesPerPage;

	public LastPageDiscriminator(ScrapingInflearnConfig scrapingInflearnConfig) {
		this.maxCoursesPerPage = scrapingInflearnConfig.getMaxCoursesPerPage();
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void markAsLastPage() {
		this.isLastPage = true;
	}

	public void checkIfLastPageBasedOnCourseCount(int courseCount) {
		if (courseCount < maxCoursesPerPage) {
			markAsLastPage();
		}
	}
}
