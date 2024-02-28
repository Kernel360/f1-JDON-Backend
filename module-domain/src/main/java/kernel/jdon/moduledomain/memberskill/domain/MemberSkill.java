package kernel.jdon.moduledomain.memberskill.domain;

import jakarta.persistence.*;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "member_skill")
@Getter
public class MemberSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", columnDefinition = "BIGINT")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id", columnDefinition = "BIGINT")
	private Skill skill;

	@Builder
	public MemberSkill(Member member, Skill skill) {
		this.member = member;
		this.skill = skill;
	}
}
