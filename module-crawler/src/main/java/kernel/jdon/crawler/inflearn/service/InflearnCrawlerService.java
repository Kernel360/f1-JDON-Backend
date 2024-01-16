package kernel.jdon.crawler.inflearn.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.UrlConfig;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InflearnCrawlerService implements CrawlerService {

	private final UrlConfig urlConfig;
	private final CourseScraperService courseScraperService;
	private final CourseParserService courseParserService;
	private final CourseStorageService courseStorageService;
	private static final int MAX_COURSES_PER_KEYWORD = 3;
	private int savedCourseCount = 0;
	private List<InflearnCourse> newCourses = new ArrayList<>();

	@Transactional
	@Override
	public void fetchCourseInfo(String skillKeyword, int pageNum) {
		String createLectureUrl = createInflearnSearchUrl(skillKeyword, CourseSearchSort.SORT_POPULARITY, pageNum);
		Elements scrapeCourseElements = courseScraperService.scrapeCourses(createLectureUrl);
		parseAndCreateCourses(scrapeCourseElements, createLectureUrl, skillKeyword, pageNum);
	}

	private String createInflearnSearchUrl(String skillKeyword, CourseSearchSort searchSort, int pageNum) {
		String path = joinToString(urlConfig.getInflearnCourseListUrl(), "/");

		String queryString = joinToString(
			createQueryString("s", skillKeyword),
			createQueryString(CourseSearchSort.SEARCH_KEY, searchSort.getSearchValue()),
			createQueryString("page", String.valueOf(pageNum))
		);

		return joinToString(path, "?", queryString);
	}

	private void parseAndCreateCourses(Elements courseElements, String lectureUrl, String skillKeyword, int pageNum) {
		for (Element courseElement : courseElements) {
			if (savedCourseCount >= MAX_COURSES_PER_KEYWORD) {
				break;
			}

			InflearnCourse parsedCourse = courseParserService.parseCourse(courseElement, lectureUrl,
				skillKeyword);

			if (parsedCourse != null) {
				newCourses.add(parsedCourse);
				savedCourseCount++;
			}
		}
		if (savedCourseCount < MAX_COURSES_PER_KEYWORD) {
			fetchCourseInfo(skillKeyword, pageNum + 1);
		}

		if (!newCourses.isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword, newCourses);
			savedCourseCount = 0;
			newCourses.clear();
		}
	}
}
