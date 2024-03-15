package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

public interface JdReader {
    WantedJd findWantedJd(Long jdId);

    List<JdInfo.FindSkill> findSkillListByWantedJd(WantedJd wantedJd);

    JdInfo.FindWantedJdListResponse findWantedJdList(PageInfoRequest pageInfoRequest, JdCondition jdCondition,
        List<String> skillKeywordList);
}
