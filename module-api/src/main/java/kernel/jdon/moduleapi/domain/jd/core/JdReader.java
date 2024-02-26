package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import kernel.jdon.wantedjd.domain.WantedJd;

public interface JdReader {
	WantedJd findWantedJd(final Long jdId);

	List<JdInfo.FindSkill> findSkillListByWantedJd(final WantedJd wantedJd);
}
