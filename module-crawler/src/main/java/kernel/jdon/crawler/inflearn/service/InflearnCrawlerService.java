package kernel.jdon.crawler.inflearn.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.config.UrlConfig;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.util.StringUtil;
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

	@Transactional
	@Override
	public void fetchCourseInfo(String skillKeyword) {
		String lectureUrl = createInflearnSearchUrl(skillKeyword, CourseSearchSort.SORT_POPULARITY, 1);
		Elements courseElements = courseScraperService.scrapeCourses(lectureUrl);
		parseAndCreateCourses(courseElements, lectureUrl, skillKeyword);
	}

	private String createInflearnSearchUrl(String skillKeyword, CourseSearchSort searchSort, int pageNum) {
		String path = StringUtil.joinToString(urlConfig.getInflearnCourseListUrl(), "/");

		String queryString = StringUtil.joinToString(
			StringUtil.createQueryString("s", skillKeyword),
			StringUtil.createQueryString(CourseSearchSort.SEARCH_KEY, searchSort.getSearchValue()),
			StringUtil.createQueryString("page", String.valueOf(pageNum))
		);

		return path + "?" + queryString;
	}

	private void parseAndCreateCourses(Elements courseElements, String lectureUrl, String skillKeyword) {
		int savedCourseCount = 0;
		List<InflearnCourse> newCourses = new ArrayList<>();

		for (Element courseElement : courseElements) {
			if (savedCourseCount >= MAX_COURSES_PER_KEYWORD) {
				break;
			}

			InflearnCourse inflearnCourse = courseParserService.parseCourse(courseElement, lectureUrl,
				skillKeyword);

			if (inflearnCourse != null) {
				newCourses.add(inflearnCourse);
				savedCourseCount++;
			}
		}

		if (!newCourses.isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword, newCourses);
		}
	}
}
