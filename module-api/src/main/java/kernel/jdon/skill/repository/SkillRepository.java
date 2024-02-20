package kernel.jdon.skill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel.jdon.skill.domain.Skill;

@Repository("legacySkillRepository")
public interface SkillRepository extends SkillDomainRepository {//, SkillRepositoryCustom {

	@Query("select s from Skill s where s.keyword != '기타' and s.jobCategory.id = :jobCategoryId")
	List<Skill> findAllByJobCategoryId(Long jobCategoryId);
}
