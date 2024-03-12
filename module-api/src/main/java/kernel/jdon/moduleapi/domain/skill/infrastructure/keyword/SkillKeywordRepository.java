package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.Optional;

import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import kernel.jdon.moduledomain.skillkeyword.repository.SkillKeywordDomainRepository;

public interface SkillKeywordRepository extends SkillKeywordDomainRepository {

    Optional<SkillKeyword> findSkillKeywordByRelatedKeywordIgnoreCase(String relatedKeyword);
}
