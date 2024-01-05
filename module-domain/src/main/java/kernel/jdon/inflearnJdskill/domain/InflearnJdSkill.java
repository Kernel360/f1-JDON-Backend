package kernel.jdon.inflearnJdskill.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.inflearn.domain.InflearnCourse;
import kernel.jdon.wantedskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inflearn_jd_skill")
public class InflearnJdSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wanted_jd_skill_id", columnDefinition = "BIGINT")
	private WantedJdSkill wantedJdSkill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", columnDefinition = "BIGINT")
	private InflearnCourse inflearnCourse;

	@Builder
	public InflearnJdSkill(WantedJdSkill wantedJdSkill, InflearnCourse inflearnCourse) {
		this.wantedJdSkill = wantedJdSkill;
		this.inflearnCourse = inflearnCourse;
	}
}
