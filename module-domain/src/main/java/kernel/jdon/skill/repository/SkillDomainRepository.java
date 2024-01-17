package kernel.jdon.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.skill.domain.Skill;

public interface SkillDomainRepository extends JpaRepository<Skill, Long> {
}