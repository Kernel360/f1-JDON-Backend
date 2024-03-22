package kernel.jdon.modulebatch.job.course.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.dto.InflearnCourseAndSkillKeywordInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemProcessor implements ItemProcessor<String, InflearnCourseAndSkillKeywordInfo> {

    private final InflearnCourseClient inflearnCourseClient;

    @Override
    public InflearnCourseAndSkillKeywordInfo process(@NonNull String keyword) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 처리 작업 시작");

        return inflearnCourseClient.getInflearnDataByKeyword(keyword);
    }
}
