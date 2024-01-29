package kernel.jdon.crawler.inflearn.service;

import static kernel.jdon.util.StringUtil.*;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.ScrapingInflearnConfig;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.crawler.inflearn.util.InflearnCrawlerState;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InflearnCrawlerService implements CrawlerService {

	private final ScrapingInflearnConfig scrapingInflearnConfig;
	private final CourseScraperService courseScraperService;
	private final CourseParserService courseParserService;
	private final CourseStorageService courseStorageService;

	@Transactional
	@Override
	public void createCourseInfo(String skillKeyword, int pageNum) {
		InflearnCrawlerState state = new InflearnCrawlerState();
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();

		while (state.getSavedCourseCount() < maxCoursesPerKeyword) {
			String createLectureUrl = createInflearnSearchUrl(skillKeyword, CourseSearchSort.SORT_POPULARITY, pageNum);
			Elements scrapeCourseElements = courseScraperService.scrapeCourses(createLectureUrl);
			parseAndCreateCourses(scrapeCourseElements, createLectureUrl, skillKeyword, pageNum, state);

			if (state.getSavedCourseCount() < maxCoursesPerKeyword) {
				pageNum++;
			}
		}

		if (!state.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword, state.getNewCourses());
			state.resetState();
		}
	}

	private String createInflearnSearchUrl(String skillKeyword, CourseSearchSort searchSort, int pageNum) {
		final String courseListUrl = scrapingInflearnConfig.getUrl();
		String path = joinToString(courseListUrl, "/");

		String queryString = joinToString(
			createQueryString("s", skillKeyword),
			createQueryString(CourseSearchSort.SEARCH_KEY, searchSort.getSearchValue()),
			createQueryString("page", String.valueOf(pageNum))
		);

		return joinToString(path, "?", queryString);
	}

	private void parseAndCreateCourses(Elements courseElements, String lectureUrl, String skillKeyword, int pageNum,
		InflearnCrawlerState state) {
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();

		for (Element courseElement : courseElements) {
			if (state.getSavedCourseCount() >= maxCoursesPerKeyword) {
				break;
			}

			InflearnCourse parsedCourse = courseParserService.parseCourse(courseElement, lectureUrl, skillKeyword);
			
			if (parsedCourse != null) {
				state.addNewCourse(parsedCourse);
				state.incrementSavedCourseCount();
			}
		}
	}
}
