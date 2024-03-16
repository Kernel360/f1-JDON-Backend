package kernel.jdon.modulebatch.jd.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.jd.reader.dto.WantedJdList;
import kernel.jdon.modulebatch.jd.repository.WantedJdRepository;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@StepScope
@Component
@RequiredArgsConstructor
public class WantedJdItemWriter implements ItemWriter<WantedJdList> {
    private final WantedJdRepository wantedJdRepository;

    @Override
    public void write(Chunk<? extends WantedJdList> chunk) throws Exception {
        System.out.println("WantedJdItemWriter 실행");
        for (WantedJdList wantedJdList : chunk) {

            for (WantedJd jd : wantedJdList.getWantedJdList()) {
                WantedJd savedWantedJd = wantedJdRepository.save(jd);
            }
            System.out.println();
        }
    }
}
