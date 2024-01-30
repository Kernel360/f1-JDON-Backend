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
	private static final int MAX_COURSES_PER_KEYWORD = 3;

	@Transactional
	@Override
	public void createCourseInfo(int pageNum) {
		List<String> keywordList = new ArrayList<>();
		keywordList.addAll(FrontendSkillType.getAllKeywords());
		keywordList.addAll(BackendSkillType.getAllKeywords());

		for (String keyword : keywordList) {
			processKeyword(keyword, pageNum);
		}
	}

	private void processKeyword(String skillKeyword, int pageNum) {
		InflearnCrawlerState state = new InflearnCrawlerState();
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();
		InflearnCrawlerState inflearnCrawlerState = new InflearnCrawlerState();

		while (state.getSavedCourseCount() < maxCoursesPerKeyword) {
			String createLectureUrl = createInflearnSearchUrl(skillKeyword, CourseSearchSort.SORT_POPULARITY, pageNum);
			Elements scrapeCourseElements = courseScraperService.scrapeCourses(createLectureUrl);
			parseAndCreateCourses(scrapeCourseElements, createLectureUrl, skillKeyword, pageNum, state);
		while (state.getSavedCourseCount() < MAX_COURSES_PER_KEYWORD && !state.isLastPage()) {
		while (inflearnCrawlerState.getSavedCourseCount() < MAX_COURSES_PER_KEYWORD
			&& !inflearnCrawlerState.isLastPage()) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			String currentUrl = createInflearnSearchUrl(skillKeyword, CourseSearchSort.SORT_POPULARITY, pageNum);
			log.info("currentUrl: {}", currentUrl);

			Elements scrapeCourseElements = courseScraperService.scrapeCourses(currentUrl);
			int coursesCount = scrapeCourseElements.size();
			inflearnCrawlerState.checkIfLastPageBasedOnCourseCount(coursesCount);

			parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, pageNum, inflearnCrawlerState);

			if (inflearnCrawlerState.getSavedCourseCount() < MAX_COURSES_PER_KEYWORD) {
			if (state.getSavedCourseCount() < maxCoursesPerKeyword) {
				pageNum++;
			}
		}

		if (!inflearnCrawlerState.getNewCourses().isEmpty()) {
			courseStorageService.createInflearnCourseAndInflearnJdSkill(skillKeyword,
				inflearnCrawlerState.getNewCourses());
			inflearnCrawlerState.resetState();
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
		InflearnCrawlerState inflearnCrawlerState) {
		InflearnCrawlerState state) {
		final int maxCoursesPerKeyword = scrapingInflearnConfig.getMaxCoursesPerKeyword();

		for (Element courseElement : courseElements) {
			if (inflearnCrawlerState.getSavedCourseCount() >= MAX_COURSES_PER_KEYWORD) {
			if (state.getSavedCourseCount() >= maxCoursesPerKeyword) {
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
