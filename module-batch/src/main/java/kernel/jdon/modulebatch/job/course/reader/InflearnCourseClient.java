package kernel.jdon.modulebatch.job.course.reader;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.global.config.ScrapingInflearnProperties;
import kernel.jdon.modulebatch.job.course.reader.condition.CourseSearchSort;
import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import kernel.jdon.modulebatch.job.course.reader.service.CourseParser;
import kernel.jdon.modulebatch.job.course.reader.service.CourseScraper;
import kernel.jdon.modulebatch.job.course.reader.service.manager.DynamicSleepTimeManager;
import kernel.jdon.modulebatch.job.course.reader.service.manager.InflearnCourseCounter;
import kernel.jdon.modulebatch.job.course.reader.service.manager.LastPageDiscriminator;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InflearnCourseClient {

    private final ScrapingInflearnProperties scrapingInflearnProperties;
    private final CourseScraper courseScraper;
    private final CourseParser courseParser;

    public InflearnCourseResponse getInflearnDataByKeyword(String keyword) {

        log.info("keyword:" + keyword);
        InflearnCourseCounter inflearnCourseCounter = new InflearnCourseCounter();
        processKeyword(keyword, inflearnCourseCounter);
        if (!inflearnCourseCounter.getNewCourseList().isEmpty()) {
            return new InflearnCourseResponse(keyword, new ArrayList<>(inflearnCourseCounter.getNewCourseList()));
        }

        return null;
    }

    private void processKeyword(String skillKeyword, InflearnCourseCounter inflearnCourseCounter) {
        final int maxCoursesPerKeyword = scrapingInflearnProperties.getMaxCoursesPerKeyword();
        int pageNum = 1;
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
    }

    private boolean scrapeAndParsePage(String currentUrl, String skillKeyword,
        InflearnCourseCounter inflearnCourseCounter, LastPageDiscriminator lastPageDiscriminator) {
        try {
            Elements scrapeCourseElements = courseScraper.scrapeCourses(currentUrl);
            int coursesCount = scrapeCourseElements.size();
            log.info("페이지에 존재하는 강의 수: " + coursesCount);
            lastPageDiscriminator.checkIfLastPageBasedOnCourseCount(coursesCount);
            parseAndCreateCourses(scrapeCourseElements, skillKeyword, inflearnCourseCounter);
            return true;
        } catch (Exception e) {
            log.error("페이지 처리 중 오류 발생: {}", currentUrl, e);
            return false;
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

    private void parseAndCreateCourses(Elements courseElements, String skillKeyword,
        InflearnCourseCounter inflearnCourseCounter) {
        final int maxCoursesPerKeyword = scrapingInflearnProperties.getMaxCoursesPerKeyword();

        for (Element courseElement : courseElements) {
            if (inflearnCourseCounter.getSavedCourseCount() >= maxCoursesPerKeyword) {
                break;
            }

            InflearnCourse parsedCourse = courseParser.parseCourse(courseElement, skillKeyword);

            if (parsedCourse != null) {
                inflearnCourseCounter.addNewCourse(parsedCourse);
                inflearnCourseCounter.incrementSavedCourseCount();
                log.info("savedCourseCount: " + inflearnCourseCounter.getSavedCourseCount());
            }
        }
    }
}

