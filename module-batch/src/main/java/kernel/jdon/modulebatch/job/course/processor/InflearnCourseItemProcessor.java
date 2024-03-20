package kernel.jdon.modulebatch.job.course.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.reader.InflearnCourseClient;
import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemProcessor implements ItemProcessor<String, InflearnCourseResponse> {

    private final InflearnCourseClient inflearnCourseClient;

    @Override
    public InflearnCourseResponse process(@NonNull String keyword) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 처리 작업 시작");

        return inflearnCourseClient.getInflearnDataByKeyword(keyword);
    }
}
