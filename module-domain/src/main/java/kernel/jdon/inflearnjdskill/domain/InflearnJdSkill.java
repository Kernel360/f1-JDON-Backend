package kernel.jdon.inflearnjdskill.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inflearn_jd_skill")
public class InflearnJdSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", columnDefinition = "BIGINT")
	private InflearnCourse inflearnCourse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id", columnDefinition = "BIGINT")
	private Skill skill;

	public static InflearnJdSkill create(InflearnCourse inflearnCourse, Skill skill) {
		InflearnJdSkill jdSkill = new InflearnJdSkill();
		jdSkill.inflearnCourse = inflearnCourse;
		jdSkill.skill = skill;
		
		return jdSkill;
	}
}
