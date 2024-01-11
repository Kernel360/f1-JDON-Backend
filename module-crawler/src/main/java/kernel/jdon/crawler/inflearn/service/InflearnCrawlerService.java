package kernel.jdon.crawler.inflearn.service;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.UrlConfig;
import kernel.jdon.crawler.inflearn.dto.CourseAndSkillsDto;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.inflearn.domain.InflearnCourse;
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
	private final CourseDuplicationCheckerService courseDuplicationCheckerService;

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
		for (Element courseElement : courseElements) {
			CourseAndSkillsDto courseAndSkillsDto = courseParserService.parseCourse(courseElement, lectureUrl,
				skillKeyword);
			if (courseAndSkillsDto == null) {
				continue;
			}
			InflearnCourse course = courseAndSkillsDto.getCourse();
			String skillTags = courseAndSkillsDto.getSkillTags();

			if (!courseDuplicationCheckerService.isDuplicate(course.getCourseId())) {
				courseStorageService.createInflearnCourseAndInflearnJdSkill(course, skillTags);
			}
		}
	}
}
