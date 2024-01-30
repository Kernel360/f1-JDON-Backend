package kernel.jdon.crawler.wanted.repository;

import java.util.List;
import java.util.Optional;

import kernel.jdon.skill.domain.Skill;
import kernel.jdon.skill.repository.SkillDomainRepository;

public interface SkillRepository extends SkillDomainRepository {
	Optional<Skill> findByJobCategoryIdAndKeyword(Long jobCategoryId, String keyword);

	List<Skill> findByKeyword(String keyword);
}
