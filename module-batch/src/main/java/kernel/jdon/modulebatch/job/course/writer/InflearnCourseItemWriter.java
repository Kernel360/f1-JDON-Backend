package kernel.jdon.modulebatch.job.course.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import kernel.jdon.modulebatch.job.course.reader.service.CourseStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemWriter implements ItemWriter<InflearnCourseResponse> {
    private final CourseStorageService courseStorageService;

    @Override
    public void write(Chunk<? extends InflearnCourseResponse> responses) throws Exception {
        log.info("write에 진입");
        for (InflearnCourseResponse response : responses) {
            log.info("inflearnCourseResponse의 skillKeyword: " + response.getSkillKeyword());
            log.info("inflearnCourseResponse의 courses: " + response.getCourses());
            courseStorageService.createInflearnCourseAndInflearnJdSkill(
                response.getSkillKeyword(),
                response.getCourses()
            );
        }
    }
}
