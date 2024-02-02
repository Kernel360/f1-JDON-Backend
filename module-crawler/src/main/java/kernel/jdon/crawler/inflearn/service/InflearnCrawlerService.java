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
import kernel.jdon.crawler.inflearn.service.infrastructure.InflearnCourseCounter;
import kernel.jdon.crawler.inflearn.service.infrastructure.LastPageDiscriminator;
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
	private static final int MAX_COURSES_PER_KEYWORD = 3;
	private static final int INITIAL_SLEEP_TIME = 2000;
	private static final int MAX_SLEEP_TIME = 10000;
	private static final int INCREMENT_SLEEP_TIME = 1000;
	private int dynamicSleepTime = INITIAL_SLEEP_TIME;

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
		InflearnCourseCounter inflearnCourseCounter = new InflearnCourseCounter();
		LastPageDiscriminator lastPageDiscriminator = new LastPageDiscriminator(scrapingInflearnConfig);

		while (inflearnCourseCounter.getSavedCourseCount() < maxCoursesPerKeyword
			&& !lastPageDiscriminator.isLastPage()) {
			try {
				Thread.sleep(dynamicSleepTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			String currentUrl = createInflearnSearchUrl(skillKeyword, pageNum);
			log.info("currentUrl: {}", currentUrl);

			boolean isSuccess = scrapeAndParsePage(currentUrl, skillKeyword, pageNum, state);
			adjustDynamicSleepTime(isSuccess);

			if (isSuccess && state.getSavedCourseCount() < MAX_COURSES_PER_KEYWORD) {
				pageNum++;
			}
		}
		saveCourseInfo(skillKeyword, state);
	}

	private boolean scrapeAndParsePage(String currentUrl, String skillKeyword, int pageNum,
		InflearnCrawlerState state) {
		try {
			Elements scrapeCourseElements = courseScraperService.scrapeCourses(currentUrl);
			int coursesCount = scrapeCourseElements.size();
			lastPageDiscriminator.checkIfLastPageBasedOnCourseCount(coursesCount);

			parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, inflearnCourseCounter);
			state.checkIfLastPageBasedOnCourseCount(coursesCount);
			parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, pageNum, state);
			return true;
		} catch (Exception e) {
			log.error("페이지 처리 중 오류 발생: {}", currentUrl, e);
			return false;
		}
	}

			if (inflearnCourseCounter.getSavedCourseCount() < maxCoursesPerKeyword) {
				pageNum++;
			}
		}
	private void adjustDynamicSleepTime(boolean requestSuccess) {
		if (requestSuccess) {
			dynamicSleepTime = INITIAL_SLEEP_TIME;
		} else {
			dynamicSleepTime = Math.min(dynamicSleepTime + INCREMENT_SLEEP_TIME, MAX_SLEEP_TIME);
		}
	}

		if (!inflearnCourseCounter.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword,
				inflearnCourseCounter.getNewCourses());
			inflearnCourseCounter.resetState();
	private void saveCourseInfo(String skillKeyword, InflearnCrawlerState state) {
		if (!state.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword, state.getNewCourses());
			state.resetState();
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
		InflearnCourseCounter inflearnCourseCounter) {
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();

		for (Element courseElement : courseElements) {
			if (inflearnCourseCounter.getSavedCourseCount() >= maxCoursesPerKeyword) {
				break;
			}

			InflearnCourse parsedCourse = courseParserService.parseCourse(courseElement, lectureUrl, skillKeyword);

			if (parsedCourse != null) {
				inflearnCourseCounter.addNewCourse(parsedCourse);
				inflearnCourseCounter.incrementSavedCourseCount();
			}
		}
	}
}
