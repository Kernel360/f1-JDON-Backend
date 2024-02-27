package kernel.jdon.moduleapi.domain.auth.core;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import kernel.jdon.moduleapi.domain.auth.dto.JdonOAuth2User;
import kernel.jdon.moduleapi.domain.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.auth.dto.UserInfoFromOAuth2;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.global.config.auth.WithdrawProperties;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;
	private final HttpSession httpSession;
	private final WithdrawProperties withdrawProperties;

	@Override
	@Transactional
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
			findMember.updateLastLoginDate();
			authorities = List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()));
		} else {
			authorities = List.of(new SimpleGrantedAuthority(MemberRole.ROLE_GUEST.name()));
		}
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
			throw new AuthException(AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL);
	}

	private void checkRightSocialProvider(Member findMember, SocialProviderType socialProvider) {
		if (!findMember.isRightSocialProvider(socialProvider))
			throw new AuthException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
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

	public boolean sendDeleteRequestToOAuth2(SessionUserInfo userInfo) {
		boolean success = false;
		if (SocialProviderType.KAKAO == userInfo.getSocialProvider()) {
			success = deleteKakaoAccount(userInfo);
			System.out.println("cu : " + success);
		} else if (SocialProviderType.GITHUB == userInfo.getSocialProvider()) {
			success = true;
		} else {
			throw new ApiException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
		}
		return success;
	}

	private boolean deleteKakaoAccount(SessionUserInfo userInfo) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "KakaoAK " + withdrawProperties.getAppAdminKey());

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("target_id_type", "user_id");
		requestBody.add("target_id", userInfo.getOAuthId());

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(withdrawProperties.getDeleteUserUrl());

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
			.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);

		boolean success = false;
		System.out.println("----------------  " + responseEntity.getStatusCode());
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			success = true;
		}
		return success;
	}
}
