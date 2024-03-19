package kernel.jdon.modulebatch.job.course.reader;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.domain.skill.BackendSkillType;
import kernel.jdon.modulebatch.domain.skill.FrontendSkillType;
import kernel.jdon.modulebatch.global.config.ScrapingInflearnProperties;
import kernel.jdon.modulebatch.job.course.reader.condition.CourseSearchSort;
import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import kernel.jdon.modulebatch.job.course.reader.service.CourseParserService;
import kernel.jdon.modulebatch.job.course.reader.service.CourseScraperService;
import kernel.jdon.modulebatch.job.course.reader.service.infrastructure.DynamicSleepTimeManager;
import kernel.jdon.modulebatch.job.course.reader.service.infrastructure.InflearnCourseCounter;
import kernel.jdon.modulebatch.job.course.reader.service.infrastructure.LastPageDiscriminator;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InflearnCourseClient {

    private final ScrapingInflearnProperties scrapingInflearnProperties;
    private final CourseScraperService courseScraperService;
    private final CourseParserService courseParserService;
    // TODO: writer로 옮기기

    public List<InflearnCourseResponse> getInflearnData() {
        List<InflearnCourseResponse> responses = new ArrayList<>();
        List<String> keywordList = new ArrayList<>();
        keywordList.addAll(FrontendSkillType.getAllKeywords());
        keywordList.addAll(BackendSkillType.getAllKeywords());
        // TODO: InflearnCourseResponse 생성해서 반환
        log.info("keywordList:" + keywordList);

        for (String keyword : keywordList) {
            log.info("keyword: " + keyword);
            InflearnCourseCounter inflearnCourseCounter = new InflearnCourseCounter();
            processKeyword(keyword, 1, inflearnCourseCounter);
            if (!inflearnCourseCounter.getNewCourses().isEmpty()) {
                responses.add(
                    new InflearnCourseResponse(keyword, new ArrayList<>(inflearnCourseCounter.getNewCourses())));
            }
        }

        return responses;
    }

    private void processKeyword(String skillKeyword, int pageNum, InflearnCourseCounter inflearnCourseCounter) {
        final int maxCoursesPerKeyword = scrapingInflearnProperties.getMaxCoursesPerKeyword();
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
            Elements scrapeCourseElements = courseScraperService.scrapeCourses(currentUrl);
            int coursesCount = scrapeCourseElements.size();
            log.info("courseCount: " + coursesCount);
            lastPageDiscriminator.checkIfLastPageBasedOnCourseCount(coursesCount);
            parseAndCreateCourses(scrapeCourseElements, currentUrl, skillKeyword, inflearnCourseCounter);
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
                log.info("savedCourseCount: " + inflearnCourseCounter.getSavedCourseCount());
            }
        }
    }
}

