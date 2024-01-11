package kernel.jdon.crawler.inflearn.service;

import static kernel.jdon.util.StringUtil.*;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.UrlConfig;
import kernel.jdon.crawler.inflearn.dto.CourseAndSkillsDto;
import kernel.jdon.crawler.inflearn.search.CourseDomain;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.crawler.inflearn.search.DevelopmentProgrammingCategory;
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
	private final CourseDuplicationCheckerService courseDuplicationCheckerService;

	@Transactional
	@Override
	public void fetchCourseInfo() {
		String lectureUrl = createInflearnListUrl(CourseDomain.DEVELOPMENT_PROGRAMMING,
			DevelopmentProgrammingCategory.WEB_DEVELOPMENT, CourseSearchSort.SORT_POPULARITY, 1);
		Elements courseElements = courseScraperService.scrapeCourses(lectureUrl);
		parseAndCreateCourses(courseElements, lectureUrl);
	}

	private void parseAndCreateCourses(Elements courseElements, String lectureUrl) {
		for (Element courseElement : courseElements) {
			CourseAndSkillsDto courseAndSkillsDto = courseParserService.parseCourse(courseElement, lectureUrl);
			InflearnCourse course = courseAndSkillsDto.getCourse();
			String skillTags = courseAndSkillsDto.getSkillTags();

			if (!courseDuplicationCheckerService.isDuplicate(course.getCourseId())) {
				courseStorageService.createInflearnCourseAndInflearnJdSkill(course, skillTags);
			}
		}
	}

	private String createInflearnListUrl(CourseDomain domain, DevelopmentProgrammingCategory category,
		CourseSearchSort searchSort, int pageNum) {
		String path = joinToString(
			urlConfig.getInflearnCourseListUrl(),
			createPathString(domain.getSearchValue()),
			createPathString(category.getSearchValue())
		);

		String queryString = joinToString(
			createQueryString(CourseSearchSort.SEARCH_KEY, searchSort.getSearchValue()),
			createQueryString("page", String.valueOf(pageNum))
		);

		if (queryString.endsWith("&")) {
			queryString = queryString.substring(0, queryString.length() - 1);
		}

		return path + "?" + queryString;
	}
}
