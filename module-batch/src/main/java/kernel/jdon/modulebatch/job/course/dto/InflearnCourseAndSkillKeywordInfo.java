package kernel.jdon.modulebatch.job.course.dto;

import java.util.List;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class InflearnCourseAndSkillKeywordInfo {
    private String skillKeyword;
    private List<InflearnCourse> inflearnCourseList;
}
