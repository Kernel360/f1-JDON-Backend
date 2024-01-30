package kernel.jdon.crawler.inflearn.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.ScrapingInflearnConfig;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.crawler.inflearn.util.InflearnCrawlerState;
import kernel.jdon.crawler.wanted.skill.BackendSkillType;
import kernel.jdon.crawler.wanted.skill.FrontendSkillType;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public void createCourseInfo() {
		List<String> keywordList = new ArrayList<>();
		keywordList.addAll(FrontendSkillType.getAllKeywords());
		keywordList.addAll(BackendSkillType.getAllKeywords());

		for (String keyword : keywordList) {
			processKeyword(keyword, 1);
		}
	}

	private void processKeyword(String skillKeyword, int pageNum) {
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();
		InflearnCrawlerState inflearnCrawlerState = new InflearnCrawlerState();

		while (inflearnCrawlerState.getSavedCourseCount() < maxCoursesPerKeyword
			&& !inflearnCrawlerState.isLastPage()) {
			try {
				Thread.sleep(scrapingInflearnConfig.getSleepTimeMillis());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			String currentUrl = createInflearnSearchUrl(skillKeyword, pageNum);
			log.info("currentUrl: {}", currentUrl);

			Elements scrapeCourseElements = courseScraperService.scrapeCourses(currentUrl);
			int coursesCount = scrapeCourseElements.size();
			inflearnCrawlerState.checkIfLastPageBasedOnCourseCount(coursesCount);

			parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, inflearnCrawlerState);

			if (inflearnCrawlerState.getSavedCourseCount() < maxCoursesPerKeyword) {
				pageNum++;
			}
		}

		if (!inflearnCrawlerState.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword,
				inflearnCrawlerState.getNewCourses());
			inflearnCrawlerState.resetState();
		}
	}

	private String createInflearnSearchUrl(String skillKeyword, int pageNum) {
		final String courseListUrl = scrapingInflearnConfig.getUrl();
		String path = joinToString(courseListUrl, "/");

		String queryString = joinToString(
			createQueryString("s", skillKeyword),
			createQueryString(CourseSearchSort.SEARCH_KEY, CourseSearchSort.SORT_POPULARITY.getSearchValue()),
			createQueryString("page", String.valueOf(pageNum))
		);

		return joinToString(path, "?", queryString);
	}

	private void parseAndCreateCourses(Elements courseElements, String lectureUrl, String skillKeyword,
		InflearnCrawlerState inflearnCrawlerState) {
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();

		for (Element courseElement : courseElements) {
			if (inflearnCrawlerState.getSavedCourseCount() >= maxCoursesPerKeyword) {
				break;
			}

			InflearnCourse parsedCourse = courseParserService.parseCourse(courseElement, lectureUrl, skillKeyword);

			if (parsedCourse != null) {
				inflearnCrawlerState.addNewCourse(parsedCourse);
				inflearnCrawlerState.incrementSavedCourseCount();
			}
		}
	}
}
