package kernel.jdon.auth.service;

import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.member.repository.MemberRepository;
import kernel.jdon.moduleapi.global.config.auth.WithdrawProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	private final MemberRepository memberRepository;
	private final WithdrawProperties withdrawProperties;

	// @Transactional
	// public Long withdraw(SessionUserInfo userInfo) {
	// 	sendDeleteRequestToOAuth2(userInfo);
	// 	memberRepository.findById(userInfo.getId())
	// 		.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER))
	// 		.withdrawMemberAccount();
	//
	// 	return userInfo.getId();
	// }
	//
	// private void sendDeleteRequestToOAuth2(SessionUserInfo userInfo) {
	// 	if (SocialProviderType.KAKAO == userInfo.getSocialProvider()) {
	// 		deleteKakaoAccount(userInfo);
	// 	} else if (SocialProviderType.GITHUB == userInfo.getSocialProvider()) {
	// 		return;
	// 	} else {
	// 		throw new ApiException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
	// 	}
	// }
	//
	// private void deleteKakaoAccount(SessionUserInfo userInfo) {
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	// 	headers.set("Authorization", "KakaoAK " + withdrawProperties.getAppAdminKey());
	//
	// 	MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
	// 	requestBody.add("target_id_type", "user_id");
	// 	requestBody.add("target_id", userInfo.getOAuthId());
	//
	// 	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(withdrawProperties.getDeleteUserUrl());
	//
	// 	HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(requestBody, headers);
	//
	// 	RestTemplate restTemplate = new RestTemplate();
	// 	ResponseEntity<String> responseEntity = restTemplate
	// 		.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
	// }
}
