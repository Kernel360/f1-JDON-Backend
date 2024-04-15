package kernel.jdon.modulebatch.domain.wantedjdskill.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.job.jd.writer.JdbcWantedJdSkillRepository;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WantedJdSkillDao {
    private final WantedJdSkillRepository wantedJdSkillRepository;
    private final JdbcWantedJdSkillRepository jdbcWantedJdSkillRepository;

    public void deleteAllByWantedJdId(final Long wantedJdId) {
        wantedJdSkillRepository.deleteAllByWantedJdId(wantedJdId);
    }

    public void saveWantedJdSkillList(
        final WantedJobDetailResponse wantedJobDetail,
        final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        jdbcWantedJdSkillRepository.saveWantedJdSkillList(wantedJobDetail, wantedJd, wantedDetailSkillList);
    }
}
