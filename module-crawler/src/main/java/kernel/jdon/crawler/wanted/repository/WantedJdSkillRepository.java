package kernel.jdon.crawler.wanted.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.wantedskill.domain.WantedJdSkill;

public interface WantedJdSkillRepository extends JpaRepository<WantedJdSkill, Long> {
}
