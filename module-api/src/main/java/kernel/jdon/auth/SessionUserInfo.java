package kernel.jdon.auth;

import java.io.Serializable;
import java.time.LocalDateTime;

import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.repository.MemberRepository;
import lombok.Getter;

@Getter
public class SessionUserInfo implements Serializable {

	private Long id;
	private String email;
	private LocalDateTime lastLoginDate;
	private MemberRole role;

	private SessionUserInfo(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.lastLoginDate = member.getLastLoginDate();
		this.role = member.getRole();
	}

	public static SessionUserInfo of(Member member) {
		return new SessionUserInfo(member);
	}
}
