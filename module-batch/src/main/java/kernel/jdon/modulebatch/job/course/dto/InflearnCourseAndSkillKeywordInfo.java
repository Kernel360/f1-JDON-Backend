package kernel.jdon.modulebatch.job.course.dto;

import java.util.List;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InflearnCourseAndSkillKeywordInfo {
    private String skillKeyword;
    private List<InflearnCourse> inflearnCourseList;

    public InflearnCourseAndSkillKeywordInfo(String skillKeyword, List<InflearnCourse> inflearnCourseList) {
        this.skillKeyword = skillKeyword;
        this.inflearnCourseList = inflearnCourseList;
    }
}
