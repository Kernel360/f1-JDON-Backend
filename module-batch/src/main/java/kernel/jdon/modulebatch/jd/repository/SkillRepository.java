package kernel.jdon.modulebatch.jd.repository;

import java.util.Optional;

import kernel.jdon.moduledomain.skill.domain.Skill;
import kernel.jdon.moduledomain.skill.repository.SkillDomainRepository;

public interface SkillRepository extends SkillDomainRepository {
    Optional<Skill> findByJobCategoryIdAndKeyword(Long jobCategoryId, String keyword);
}
