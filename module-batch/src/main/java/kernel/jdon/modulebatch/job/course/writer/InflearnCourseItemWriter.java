package kernel.jdon.modulebatch.job.course.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.dto.InflearnCourseAndSkillKeywordInfo;
import kernel.jdon.modulebatch.job.course.writer.service.CourseStorer;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemWriter implements ItemWriter<InflearnCourseAndSkillKeywordInfo> {
    private final CourseStorer courseStorer;

    @Override
    public void write(Chunk<? extends InflearnCourseAndSkillKeywordInfo> responses) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 쓰기 작업 시작");
        for (InflearnCourseAndSkillKeywordInfo response : responses) {
            log.info("inflearnCourseResponse의 기술 스택: " + response.getSkillKeyword());
            for (InflearnCourse course : response.getInflearnCourseList()) {
                log.info("inflearnCourseResponse의 강의 제목: " + course.getTitle());
            }
            courseStorer.createInflearnCourseAndInflearnJdSkill(
                response.getSkillKeyword(),
                response.getInflearnCourseList()
            );
        }
    }
}
