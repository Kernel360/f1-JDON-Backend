package kernel.jdon.moduleapi.global.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SessionUserInfo implements Serializable {

	private Long id;
	private String email;
	private String oauthId;
	private SocialProviderType socialProvider;
	private LocalDateTime lastLoginDate;
	private MemberRole role;

	@Builder
	private SessionUserInfo(Long id, String email, String oauthId, SocialProviderType socialProvider,
		LocalDateTime lastLoginDate, MemberRole role) {
		this.id = id;
		this.email = email;
		this.oauthId = oauthId;
		this.socialProvider = socialProvider;
		this.lastLoginDate = lastLoginDate;
		this.role = role;
	}

	public static SessionUserInfo of(String email, String oauthId, SocialProviderType provider) {
		return SessionUserInfo.builder()
			.email(email)
			.oauthId(oauthId)
			.socialProvider(provider)
			.build();
	}

	public SessionUserInfo getMemberSession(Member member) {
		this.id = member.getId();
		this.lastLoginDate = member.getLastLoginDate();
		this.role = member.getRole();

		return this;
	}
}
