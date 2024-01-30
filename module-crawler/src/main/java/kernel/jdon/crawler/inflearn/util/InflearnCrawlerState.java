package kernel.jdon.crawler.inflearn.util;

import java.util.ArrayList;
import java.util.List;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.Getter;

@Getter
public class InflearnCrawlerState {
	private int savedCourseCount = 0;
	private List<InflearnCourse> newCourses = new ArrayList<>();
	private boolean isLastPage = false;
	private static final int MAX_COURSES_PER_PAGE = 24;

	public void incrementSavedCourseCount() {
		this.savedCourseCount++;
	}

	public void addNewCourse(InflearnCourse course) {
		newCourses.add(course);
	}

	public void resetState() {
		savedCourseCount = 0;
		newCourses.clear();
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void markAsLastPage() {
		this.isLastPage = true;
	}

	public void checkIfLastPageBasedOnCourseCount(int courseCount) {
		if (courseCount < MAX_COURSES_PER_PAGE) {
			markAsLastPage();
		}
	}
}
