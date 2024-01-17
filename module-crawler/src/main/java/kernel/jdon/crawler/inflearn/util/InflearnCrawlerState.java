package kernel.jdon.crawler.inflearn.util;

import java.util.ArrayList;
import java.util.List;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public class InflearnCrawlerState {
	private int savedCourseCount = 0;
	private List<InflearnCourse> newCourses = new ArrayList<>();

	public int getSavedCourseCount() {
		return savedCourseCount;
	}

	public void incrementSavedCourseCount() {
		this.savedCourseCount++;
	}

	public List<InflearnCourse> getNewCourses() {
		return newCourses;
	}

	public void addNewCourse(InflearnCourse course) {
		newCourses.add(course);
	}

	public void resetState() {
		savedCourseCount = 0;
		newCourses.clear();
	}
}
