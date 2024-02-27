package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.wantedjd.domain.WantedJd;

public interface JdReader {
	WantedJd findWantedJd(Long jdId);

	List<JdInfo.FindSkill> findSkillListByWantedJd(WantedJd wantedJd);

	JdInfo.FindWantedJdListResponse findWantedJdList(PageInfoRequest pageInfoRequest, String keyword);
}
