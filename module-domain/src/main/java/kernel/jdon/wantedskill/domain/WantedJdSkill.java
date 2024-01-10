package kernel.jdon.wantedskill.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wanted_jd_skill")
public class WantedJdSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id", columnDefinition = "BIGINT", nullable = false)
	private Skill skill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wanted_jd_id", columnDefinition = "BIGINT", nullable = false)
	private WantedJd wantedJd;

	@Builder
	public WantedJdSkill(Skill skill, WantedJd wantedJd) {
		this.skill = skill;
		this.wantedJd = wantedJd;
	}
}
