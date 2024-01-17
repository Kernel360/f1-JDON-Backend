package kernel.jdon.crawler.wanted.repository;

import java.util.Optional;

import kernel.jdon.skill.domain.Skill;
import kernel.jdon.skill.repository.SkillDomainRepository;

public interface SkillRepository extends SkillDomainRepository {
	Optional<Skill> findByJobCategoryIdAndKeyword(Long jobCategoryId, String keyword);
	Optional<Skill> findByKeyword(String keyword);
}
