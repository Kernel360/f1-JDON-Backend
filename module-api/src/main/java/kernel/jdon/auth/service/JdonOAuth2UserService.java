package kernel.jdon.auth.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JdonOAuth2UserService extends DefaultOAuth2UserService {

	@Override
	// 리소스 서버에서 보내주는 사용자 정보를 불러오는 메서드 loadUser
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("userRequest: {}", userRequest.getAccessToken().getTokenValue());
		OAuth2User user = super.loadUser(userRequest);
		log.info("user: {}", user.getAttributes());

		Map<String, Object> attributes = user.getAttributes();
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			log.info("key: {}, value: {}", entry.getKey(), entry.getValue());
		}

		return user;
	}
}
