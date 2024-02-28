package kernel.jdon.moduledomain.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.skill.domain.Skill;

public interface SkillDomainRepository extends JpaRepository<Skill, Long> {
}