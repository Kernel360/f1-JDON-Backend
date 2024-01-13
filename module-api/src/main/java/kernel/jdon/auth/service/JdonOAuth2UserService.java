package kernel.jdon.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.auth.dto.JdonOAuth2User;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JdonOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);

		return getOAuth2UserFromOAuthServer(userRequest, user);
	}

	private DefaultOAuth2User getOAuth2UserFromOAuthServer(OAuth2UserRequest userRequest, OAuth2User user) {
		SocialProviderType socialProvider = getSocialProvider(userRequest);
		String email = null;
		if (SocialProviderType.KAKAO == socialProvider) {
			email = getEmailFromKakao(user);
		}

		Member findMember = memberRepository.findByEmail(email);
		List<SimpleGrantedAuthority> authorities = null;
		if (findMember != null && findMember.isActiveMember()) {
			checkRightSocialProvider(findMember, socialProvider);
			httpSession.setAttribute("USER", SessionUserInfo.of(findMember));
			authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			authorities = List.of(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));
		}
		return new JdonOAuth2User(authorities, user.getAttributes(), getUserNameAttributeName(userRequest),
			email, socialProvider);
	}

	private String getEmailFromKakao(OAuth2User user) {
		Map<String, Object> attributes = user.getAttributes();
		return ((Map<String, String>)attributes.get("kakao_account")).get("email");
	}

	private void checkRightSocialProvider(Member findMember, SocialProviderType socialProvider) {
		if (!findMember.isRightSocialProvider(socialProvider))
			throw new ApiException(AuthErrorCode.NOT_FOUND_NOT_MATCH_PROVIDER_TYPE);
	}

	private SocialProviderType getSocialProvider(OAuth2UserRequest userRequest) {
		return SocialProviderType.valueOf((userRequest.getClientRegistration().getRegistrationId()).toUpperCase());
	}

	private String getUserNameAttributeName(OAuth2UserRequest userRequest) {
		return userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();
	}
}
