package kernel.jdon.skillhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.skillhistory.domain.SkillHistory;

public interface SkillHistoryDomainRepository extends JpaRepository<SkillHistory, Long> {
}
