package kernel.jdon.crawler.inflearn.service.infrastructure;

import kernel.jdon.crawler.config.ScrapingInflearnProperties;

public class LastPageDiscriminator {
	private boolean isLastPage = false;
	private final int maxCoursesPerPage;

	public LastPageDiscriminator(ScrapingInflearnProperties scrapingInflearnProperties) {
		this.maxCoursesPerPage = scrapingInflearnProperties.getMaxCoursesPerPage();
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
