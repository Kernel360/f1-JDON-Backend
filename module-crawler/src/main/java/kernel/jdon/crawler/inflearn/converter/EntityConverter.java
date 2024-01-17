package kernel.jdon.crawler.inflearn.converter;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityConverter {
	public static InflearnCourse createInflearnCourse(Long courseId, String title, String lectureUrl, String instructor,
		long studentCount, String imageUrl, int price) {
		return InflearnCourse.builder()
			.courseId(courseId)
			.title(title)
			.lectureUrl(lectureUrl)
			.instructor(instructor)
			.studentCount(studentCount)
			.imageUrl(imageUrl)
			.price(price)
			.build();
	}

	public static InflearnJdSkill createInflearnJdSkill(InflearnCourse course, Skill skill) {
		return InflearnJdSkill.builder()
			.inflearnCourse(course)
			.skill(skill)
			.build();
	}
}
