package kernel.jdon.skill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.skill.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	Optional<Skill> findByJobCategoryIdAndKeyword(Long jobCategoryId, String keyword);

	Optional<Skill> findByKeyword(String keyword);
}