package kernel.jdon.crawler.inflearn.service;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kernel.jdon.crawler.inflearn.converter.EntityBuilder;
import kernel.jdon.crawler.inflearn.dto.CourseAndSkillsDto;
import kernel.jdon.crawler.inflearn.util.SkillStandardizer;
import kernel.jdon.inflearn.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseParserService {

	public CourseAndSkillsDto parseCourse(Element courseElement, String lectureUrl) {
		Long courseId = Long.parseLong(courseElement.attr("data-productId"));
		String title = getText(courseElement, "div.course_title");
		long studentCount = parseStudentCount(courseElement);
		String instructor = getText(courseElement, "div.instructor");
		String imageUrl = courseElement.select("img").attr("src");
		int price = Integer.parseInt(parsePrice(courseElement));
		String skillTags = getText(courseElement, "div.course_skills > span");
		String standardizedSkillTags = SkillStandardizer.standardize(skillTags);

		InflearnCourse inflearnCourse = EntityBuilder.createInflearnCourse(courseId, title, lectureUrl, instructor,
			studentCount, imageUrl, price);

		return new CourseAndSkillsDto(inflearnCourse, standardizedSkillTags);
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
