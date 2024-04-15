package kernel.jdon.modulecrawler.domain.jd.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.modulecrawler.domain.jd.core.JdReader;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdReaderImpl implements JdReader {
    private final WantedJdRepository wantedJdRepository;

    @Override
    public boolean existsByJobCategoryAndDetailId(final JobCategory jobCategory, final Long detailId) {
        return wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, detailId);
    }

    @Override
    public WantedJd findByJobCategoryAndDetailId(final JobCategory jobCategory, final Long jobDetailId) {
        return wantedJdRepository.findByJobCategoryAndDetailId(jobCategory, jobDetailId);
    }
}
