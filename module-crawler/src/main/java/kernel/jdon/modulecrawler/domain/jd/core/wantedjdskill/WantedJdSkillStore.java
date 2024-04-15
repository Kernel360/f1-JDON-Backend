package kernel.jdon.modulecrawler.domain.jd.core.wantedjdskill;

import java.util.List;

import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

public interface WantedJdSkillStore {
    void saveWantedJdSkillList(WantedJobDetailResponse wantedJobDetail, WantedJd wantedJd,
        List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList);
}
