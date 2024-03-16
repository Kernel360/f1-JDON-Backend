package kernel.jdon.modulebatch.jd.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.jd.reader.dto.WantedJdList;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@StepScope
@Component
@RequiredArgsConstructor
public class WantedJdItemProcessor implements ItemProcessor<WantedJobDetailListResponse, WantedJdList> {

    @Override
    public WantedJdList process(WantedJobDetailListResponse item) throws Exception {
        List<WantedJd> wantedJdList = item.getJobDetailList().stream()
            .map(WantedJobDetailResponse::toWantedJdEntity)
            .collect(Collectors.toList());

        return new WantedJdList(wantedJdList);
    }
}
