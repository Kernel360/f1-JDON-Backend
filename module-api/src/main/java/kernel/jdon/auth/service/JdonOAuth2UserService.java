package kernel.jdon.auth.service;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.auth.dto.JdonOAuth2User;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.auth.dto.UserInfoFromOAuth2;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.global.exception.UnAuthorizedException;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
		UserInfoFromOAuth2 userInfo = null;
		if (SocialProviderType.KAKAO == socialProvider) {
			userInfo = getUserInfoFromKakao(user);
		} else if (SocialProviderType.GITHUB == socialProvider) {
			userInfo = getUserInfoFromGithub(user);
		}

		Member findMember = memberRepository.findByEmail(userInfo.getEmail());
		List<SimpleGrantedAuthority> authorities = null;
		if (findMember != null && findMember.isActiveMember()) {
			checkRightSocialProvider(findMember, userInfo.getSocialProvider());
			httpSession.setAttribute("USER", SessionUserInfo.of(findMember, userInfo));
			authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			authorities = List.of(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));
		}
		log.info("email: {}", userInfo.getEmail());
		return new JdonOAuth2User(authorities, user.getAttributes(), getUserNameAttributeName(userRequest),
			userInfo.getEmail(), userInfo.getSocialProvider());
	}

	private UserInfoFromOAuth2 getUserInfoFromGithub(OAuth2User user) {
		String email = (String)user.getAttributes().get("email");
		isEmailExist(email);
		String oAuthId = String.valueOf(user.getAttributes().get("id"));

		return UserInfoFromOAuth2.of(email, oAuthId, SocialProviderType.GITHUB);
	}

	private UserInfoFromOAuth2 getUserInfoFromKakao(OAuth2User user) {
		Map<String, Object> attributes = user.getAttributes();
		String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");
		isEmailExist(email);
		String oAuthId = String.valueOf(attributes.get("id"));

		return UserInfoFromOAuth2.of(email, oAuthId, SocialProviderType.KAKAO);
	}

	private void isEmailExist(String email) {
		if (email == null)
			throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL);
	}

	private void checkRightSocialProvider(Member findMember, SocialProviderType socialProvider) {
		if (!findMember.isRightSocialProvider(socialProvider))
			throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
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
