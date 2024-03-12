package kernel.jdon.modulecrawler.wanted.repository;

import java.util.List;
import java.util.Optional;

import kernel.jdon.moduledomain.skill.domain.Skill;
import kernel.jdon.moduledomain.skill.repository.SkillDomainRepository;

public interface SkillRepository extends SkillDomainRepository {
    Optional<Skill> findByJobCategoryIdAndKeyword(Long jobCategoryId, String keyword);

    List<Skill> findByKeyword(String keyword);
}
