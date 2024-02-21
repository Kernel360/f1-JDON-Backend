package kernel.jdon.crawler.inflearn.service;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.config.ScrapingInflearnProperties;
import kernel.jdon.crawler.inflearn.search.CourseSearchSort;
import kernel.jdon.crawler.inflearn.service.infrastructure.DynamicSleepTimeManager;
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

	private final ScrapingInflearnProperties scrapingInflearnProperties;
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
		final int maxCoursesPerKeyword = scrapingInflearnProperties.getMaxCoursesPerKeyword();
		InflearnCourseCounter inflearnCourseCounter = new InflearnCourseCounter();
		LastPageDiscriminator lastPageDiscriminator = new LastPageDiscriminator(scrapingInflearnProperties);
		DynamicSleepTimeManager sleepTimeManager = new DynamicSleepTimeManager(scrapingInflearnProperties);

		while (inflearnCourseCounter.getSavedCourseCount() < maxCoursesPerKeyword
			&& !lastPageDiscriminator.isLastPage()) {
			try {
				Thread.sleep(sleepTimeManager.getDynamicSleepTime());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			String currentUrl = createInflearnSearchUrl(skillKeyword, pageNum);
			log.info("currentUrl: {}", currentUrl);

			boolean isSuccess = scrapeAndParsePage(currentUrl, skillKeyword, inflearnCourseCounter,
				lastPageDiscriminator);
			sleepTimeManager.adjustSleepTime(isSuccess);

			if (isSuccess && inflearnCourseCounter.getSavedCourseCount() < maxCoursesPerKeyword) {
				pageNum++;
			}
		}

		saveCourseInfo(skillKeyword, inflearnCourseCounter);
	}

	private boolean scrapeAndParsePage(String currentUrl, String skillKeyword,
		InflearnCourseCounter inflearnCourseCounter, LastPageDiscriminator lastPageDiscriminator) {
		try {
			Elements scrapeCourseElements = courseScraperService.scrapeCourses(currentUrl);
			int coursesCount = scrapeCourseElements.size();
			lastPageDiscriminator.checkIfLastPageBasedOnCourseCount(coursesCount);
			parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, inflearnCourseCounter);
			return true;
		} catch (Exception e) {
			log.error("페이지 처리 중 오류 발생: {}", currentUrl, e);
			return false;
		}
	}

	private void saveCourseInfo(String skillKeyword, InflearnCourseCounter inflearnCourseCounter) {
		if (!inflearnCourseCounter.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword,
				inflearnCourseCounter.getNewCourses());
			inflearnCourseCounter.resetState();
		}
	}

	private String createInflearnSearchUrl(String skillKeyword, int pageNum) {
		final String courseListUrl = scrapingInflearnProperties.getUrl();
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
		final int maxCoursesPerKeyword = scrapingInflearnProperties.getMaxCoursesPerKeyword();

		for (Element courseElement : courseElements) {
			if (inflearnCourseCounter.getSavedCourseCount() >= maxCoursesPerKeyword) {
				break;
			}

			InflearnCourse parsedCourse = courseParserService.parseCourse(courseElement, skillKeyword);

			if (parsedCourse != null) {
				inflearnCourseCounter.addNewCourse(parsedCourse);
				inflearnCourseCounter.incrementSavedCourseCount();
			}
		}
	}
}
