package kernel.jdon.modulecrawler.domain.jd.infrastructure.wantedjdskill;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;
import kernel.jdon.modulecrawler.domain.jd.core.wantedjdskill.WantedJdSkillStore;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WantedJdSkillStoreImpl implements WantedJdSkillStore {
    private final JdbcWantedJdSkillRepository jdbcWantedJdSkillRepository;

    @Override
    public void saveWantedJdSkillList(final WantedJobDetailResponse wantedJobDetail, final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        jdbcWantedJdSkillRepository.saveWantedJdSkillList(wantedJobDetail, wantedJd, wantedDetailSkillList);

    }
}
