package kernel.jdon.moduledomain.skillhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.skillhistory.domain.SkillHistory;

public interface SkillHistoryDomainRepository extends JpaRepository<SkillHistory, Long> {
}
