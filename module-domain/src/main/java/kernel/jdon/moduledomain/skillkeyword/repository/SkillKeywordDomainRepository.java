package kernel.jdon.moduledomain.skillkeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;

public interface SkillKeywordDomainRepository extends JpaRepository<SkillKeyword, Long> {
}
