package kernel.jdon.modulebatch.job.course.reader.service.manager;

import java.util.ArrayList;
import java.util.List;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.Getter;

@Getter
public class InflearnCourseCounter {
    private final List<InflearnCourse> newCourseList = new ArrayList<>();
    private int savedCourseCount = 0;

    public void incrementSavedCourseCount() {
        this.savedCourseCount++;
    }

    public void addNewCourse(InflearnCourse course) {
        newCourseList.add(course);
    }

    public void resetState() {
        savedCourseCount = 0;
        newCourseList.clear();
    }
}
