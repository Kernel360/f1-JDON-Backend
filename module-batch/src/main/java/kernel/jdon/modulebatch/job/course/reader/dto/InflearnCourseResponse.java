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
    private List<InflearnCourse> courses;

    public InflearnCourseResponse(String skillKeyword, List<InflearnCourse> courses) {
        this.skillKeyword = skillKeyword;
        this.courses = courses;
    }
}
