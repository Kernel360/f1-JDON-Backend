package kernel.jdon.inflearnjdskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.wantedjdskill.domain.WantedJdSkill;

public interface InflearnJdSkillRepository extends JpaRepository<InflearnJdSkill, Long> {

	boolean existsByInflearnCourseAndWantedJdSkill(InflearnCourse inflearnCourse, WantedJdSkill wantedJdSkill);
}
