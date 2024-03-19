package kernel.jdon.modulebatch.job.course.reader.service;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kernel.jdon.modulebatch.global.config.ScrapingInflearnProperties;
import kernel.jdon.modulebatch.job.course.reader.converter.EntityConverter;
import kernel.jdon.modulebatch.job.course.reader.converter.SkillStandardizer;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseParserService {

    private final CourseKeywordAnalysisService courseKeywordAnalysisService;
    private final ScrapingInflearnProperties scrapingInflearnProperties;

    public InflearnCourse parseCourse(Element courseElement, String skillKeyword) {
        Long courseId = Long.parseLong(courseElement.attr("data-productId"));
        String title = getText(courseElement, "div.course_title");
        String description = getText(courseElement, "p.course_description");
        String relativeLecturePath = courseElement.select("a.course_card_front").attr("href");
        String lectureUrl = scrapingInflearnProperties.getDetailUrlPrefix() + relativeLecturePath;
        log.info("title: " + title);
        log.info("description: " + description);

        if (!courseKeywordAnalysisService.isKeywordPresentInTitleAndDescription(title, description,
            SkillStandardizer.standardize(skillKeyword))) {
            return null;
        }

        long studentCount = parseStudentCount(courseElement);
        String instructor = getText(courseElement, "div.instructor");
        String imageUrl = courseElement.select("img").attr("src");
        int price = Integer.parseInt(parsePrice(courseElement));

        return EntityConverter.createInflearnCourse(courseId, title, lectureUrl, instructor,
            studentCount, imageUrl, price);
    }

    private String getText(Element element, String cssQuery) {

        return element.select(cssQuery).text();
    }

    private long parseStudentCount(Element element) {
        String studentCountText = element.select("div.tags span.tag:contains(명)").text();
        String parsedText = studentCountText.replaceAll("[^\\d]", "");

        return parsedText.isEmpty() ? 0 : Long.parseLong(parsedText);
    }

    private String parsePrice(Element element) {
        Elements priceElement = element.select("div.price");
        String priceText =
            priceElement.select("del").isEmpty() ? priceElement.text() : priceElement.select("del").first().text();
        String parsedText = priceText.replaceAll("[^\\d]", "");

        return parsedText.isEmpty() ? "0" : parsedText;
    }
}
