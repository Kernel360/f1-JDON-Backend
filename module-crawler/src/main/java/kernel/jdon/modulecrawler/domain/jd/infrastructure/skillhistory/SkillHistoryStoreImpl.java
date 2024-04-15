package kernel.jdon.modulecrawler.domain.jd.infrastructure.skillhistory;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;
import kernel.jdon.modulecrawler.domain.jd.core.skillhistory.SkillHistoryStore;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillHistoryStoreImpl implements SkillHistoryStore {
    private final JdbcSkillHistoryRepository jdbcSkillHistoryRepository;

    @Override
    public void saveSkillHistoryList(final Long jobCategoryId, final Long wantedJdId,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        jdbcSkillHistoryRepository.saveSkillHistoryList(jobCategoryId, wantedJdId, wantedDetailSkillList);
    }
}
