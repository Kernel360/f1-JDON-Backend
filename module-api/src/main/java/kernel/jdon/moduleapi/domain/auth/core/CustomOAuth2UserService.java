package kernel.jdon.moduleapi.domain.auth.core;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.global.config.auth.WithdrawProperties;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService {
	private final WithdrawProperties withdrawProperties;

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
