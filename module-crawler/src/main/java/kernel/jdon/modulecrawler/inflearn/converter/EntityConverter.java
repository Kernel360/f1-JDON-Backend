package kernel.jdon.modulecrawler.inflearn.converter;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.moduledomain.skill.domain.Skill;
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
