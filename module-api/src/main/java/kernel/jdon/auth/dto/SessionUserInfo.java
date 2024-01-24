package kernel.jdon.auth.dto;

import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class SessionUserInfo implements Serializable {

	private Long id;
	private String email;
	private String oAuthId;
	private SocialProviderType socialProvider;
	private LocalDateTime lastLoginDate;
	private MemberRole role;

	private SessionUserInfo(Member member, UserInfoFromOAuth2 userInfo) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.oAuthId = userInfo.getOAuthId();
		this.socialProvider = userInfo.getSocialProvider();
		this.lastLoginDate = member.getLastLoginDate();
		this.role = member.getRole();
	}

	public static SessionUserInfo of(Member member, UserInfoFromOAuth2 userInfo) {
		return new SessionUserInfo(member, userInfo);
	}
}
