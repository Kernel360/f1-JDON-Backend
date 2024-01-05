package kernel.jdon.crawler.inflearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearn.domain.InflearnCourse;
import kernel.jdon.inflearnJdskill.domain.InflearnJdSkill;
import kernel.jdon.wantedskill.domain.WantedJdSkill;

public interface InflearnJdSkillRepository extends JpaRepository<InflearnJdSkill, Long> {

	boolean existsByInflearnCourseAndWantedJdSkill(InflearnCourse inflearnCourse, WantedJdSkill wantedJdSkill);
}
