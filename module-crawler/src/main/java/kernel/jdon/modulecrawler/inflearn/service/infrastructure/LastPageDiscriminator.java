package kernel.jdon.modulecrawler.inflearn.service.infrastructure;

import kernel.jdon.modulecrawler.config.ScrapingInflearnProperties;

public class LastPageDiscriminator {
	private final int maxCoursesPerPage;
	private boolean isLastPage = false;

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
