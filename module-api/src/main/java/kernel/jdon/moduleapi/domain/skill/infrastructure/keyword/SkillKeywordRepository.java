package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.List;

import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import kernel.jdon.moduledomain.skillkeyword.repository.SkillKeywordDomainRepository;

public interface SkillKeywordRepository extends SkillKeywordDomainRepository {

    List<SkillKeyword> findAllByRelatedKeywordIgnoreCase(String relatedKeyword);
}
