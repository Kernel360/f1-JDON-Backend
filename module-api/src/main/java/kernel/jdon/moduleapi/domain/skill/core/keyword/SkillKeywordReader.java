package kernel.jdon.moduleapi.domain.skill.core.keyword;

import java.util.List;

import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;

public interface SkillKeywordReader {
    List<SkillKeyword> findAllByRelatedKeywordIgnoreCase(String relatedKeyword);
}
