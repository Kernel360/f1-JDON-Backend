package kernel.jdon.moduleapi.domain.skill.core.keyword;

import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;

public interface SkillKeywordReader {
    SkillKeyword findSkillKeywordByRelatedKeywordIgnoreCase(String relatedKeyword);
}
