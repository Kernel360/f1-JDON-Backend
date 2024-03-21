package kernel.jdon.modulebatch.domain.skillhistory.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillHistoryStore {
    private final JdbcSkillHistoryRepository jdbcSkillHistoryRepository;
    private final SkillHistoryRepository skillHistoryRepository;

    public void saveSkillHistoryList(final Long jobCategoryId, final Long wantedJdId,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        jdbcSkillHistoryRepository.saveSkillHistoryList(jobCategoryId, wantedJdId, wantedDetailSkillList);
    }

    public void deleteAllByWantedJdId(final Long wantedJdId) {
        skillHistoryRepository.deleteAllByWantedJdId(wantedJdId);
    }
}
