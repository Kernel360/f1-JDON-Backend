package kernel.jdon.moduleapi.domain.skill.core.wantedjd;

import java.util.List;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;

public interface WantedJdSkillReader {
    List<SkillInfo.FindJd> findWantedJdListBySkill(final String keyword);
}
