package kernel.jdon.memberskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.memberskill.domain.MemberSkill;

public interface MemberSkillDomainRepository extends JpaRepository<MemberSkill, Long> {

}