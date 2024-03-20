package kernel.jdon.modulebatch.job.course.reader.dto;

import java.util.List;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InflearnCourseResponse {
    private String skillKeyword;
    private List<InflearnCourse> inflearnCourseList;

    public InflearnCourseResponse(String skillKeyword, List<InflearnCourse> inflearnCourseList) {
        this.skillKeyword = skillKeyword;
        this.inflearnCourseList = inflearnCourseList;
    }
}
