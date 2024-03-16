package kernel.jdon.modulebatch.jd.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import lombok.RequiredArgsConstructor;

@StepScope
@Component
@RequiredArgsConstructor
public class WantedJdItemProcessor implements ItemProcessor<WantedJobDetailListResponse, WantedJobDetailListResponse> {

    @Override
    public WantedJobDetailListResponse process(WantedJobDetailListResponse item) throws Exception {
        return item;
    }
}
