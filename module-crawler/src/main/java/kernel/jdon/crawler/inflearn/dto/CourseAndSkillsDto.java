package kernel.jdon.crawler.inflearn.dto;

import kernel.jdon.inflearn.domain.InflearnCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseAndSkillsDto {
	private final InflearnCourse course;
	private final String skillTags;
}
