package kernel.jdon.inflearncourseskill.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.inflearn.domain.InflearnCourse;
import kernel.jdon.inflearnskill.domain.InflearnSkill;

@Entity
@Table(name = "inflearn_course_skill")
public class InflearnCourseSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id", columnDefinition = "BIGINT")
	private InflearnSkill inflearnSkill;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", columnDefinition = "BIGINT")
	private InflearnCourse inflearnCourse;
}
