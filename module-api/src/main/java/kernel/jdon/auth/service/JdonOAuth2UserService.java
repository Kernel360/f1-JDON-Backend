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
import kernel.jdon.auth.JdonOAuth2User;
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
		SocialProviderType socialProvider = SocialProviderType.valueOf((
			userRequest.getClientRegistration().getRegistrationId()).toUpperCase());
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();
		if (SocialProviderType.KAKAO == socialProvider) {
			return getEmailFromKakao(user, userNameAttributeName);
		}
		return user;
	}

	private DefaultOAuth2User getEmailFromKakao(OAuth2User user, String userNameAttributeName) {
		Map<String, Object> attributes = user.getAttributes();
		String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");

		Member findMember = memberRepository.findByEmail(email);
		if (findMember != null && findMember.isActiveMember()) {
			checkRightSocialProvider(findMember, SocialProviderType.KAKAO);
			httpSession.setAttribute("USER", email);
			return new JdonOAuth2User(user.getAuthorities(), attributes, userNameAttributeName, email,
				SocialProviderType.KAKAO);
		} else {
			List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));
			return new JdonOAuth2User(authorities, attributes, userNameAttributeName, email,
				SocialProviderType.KAKAO);
		}
	}

	private void checkRightSocialProvider(Member findMember, SocialProviderType socialProviderType) {
		if (!findMember.isRightSocialProvider(socialProviderType))
			throw new IllegalArgumentException("다른 소셜 로그인으로 가입된 이메일입니다.");
	}
}
