package kernel.jdon.moduleapi.domain.auth.core;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.dto.JdonOAuth2User;
import kernel.jdon.moduleapi.domain.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService {
	private final HttpSession httpSession;
	private final MemberReader memberReader;
	private final MemberStore memberStore;
	private final OAuth2ProviderComposite oauth2ProviderComposite;

	@Override
	@Transactional
	public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		final OAuth2User user = super.loadUser(userRequest);

		return getOAuth2User(userRequest, user);
	}

	private DefaultOAuth2User getOAuth2User(final OAuth2UserRequest userRequest, final OAuth2User user) {
		final SessionUserInfo userInfo = oauth2ProviderComposite.getClient(getSocialProvider(userRequest))
			.getUserInfo(user);
		final Member findMember = memberReader.findByEmail(userInfo.getEmail());
		final List<SimpleGrantedAuthority> authorities = getAuthorities(userInfo, findMember);

		return new JdonOAuth2User(authorities, user.getAttributes(), getUserNameAttributeName(userRequest),
			userInfo.getEmail(), userInfo.getSocialProvider());
	}

	private List<SimpleGrantedAuthority> getAuthorities(final SessionUserInfo userInfo, final Member member) {
		if (member != null && member.isActiveMember()) {
			checkRightSocialProvider(member, userInfo.getSocialProvider());
			httpSession.setAttribute("USER", userInfo.getMemberSession(member));
			memberStore.updateLastLoginDate(member);
			return List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()));
		} else {
			return List.of(new SimpleGrantedAuthority(MemberRole.ROLE_GUEST.name()));
		}
	}

	private void checkRightSocialProvider(final Member member, final SocialProviderType socialProvider) {
		if (!member.isRightSocialProvider(socialProvider))
			throw new AuthException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
	}

	private SocialProviderType getSocialProvider(final OAuth2UserRequest userRequest) {
		return SocialProviderType.valueOf((userRequest.getClientRegistration().getRegistrationId()).toUpperCase());
	}

	private String getUserNameAttributeName(final OAuth2UserRequest userRequest) {
		return userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();
	}

	public boolean sendDeleteRequestToOAuth2(final SessionUserInfo userInfo) {
		return oauth2ProviderComposite.getClient(userInfo.getSocialProvider()).unlinkOAuth2Account(userInfo);
	}
}
