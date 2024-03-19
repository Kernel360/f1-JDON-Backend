package kernel.jdon.modulebatch.job.course.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.reader.InflearnCourseClient;
import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import lombok.RequiredArgsConstructor;

@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemProcessor implements ItemProcessor<String, InflearnCourseResponse> {

    private final InflearnCourseClient inflearnCourseClient;

    @Override
    public InflearnCourseResponse process(String keyword) throws Exception {

        return inflearnCourseClient.getInflearnDataByKeyword(keyword);
    }
}
