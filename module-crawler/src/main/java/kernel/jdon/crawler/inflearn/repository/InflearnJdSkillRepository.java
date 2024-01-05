package kernel.jdon.crawler.inflearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel.jdon.inflearn.domain.InflearnCourse;
import kernel.jdon.inflearnJdskill.domain.InflearnJdSkill;
import kernel.jdon.wantedskill.domain.WantedJdSkill;

@Repository
public interface InflearnJdSkillRepository extends JpaRepository<InflearnJdSkill, Long> {

	boolean existsByInflearnCourseAndWantedJdSkill(InflearnCourse inflearnCourse, WantedJdSkill wantedJdSkill);
}
