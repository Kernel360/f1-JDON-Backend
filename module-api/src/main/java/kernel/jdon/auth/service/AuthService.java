package kernel.jdon.auth.service;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.app-admin-key}")
    private String kakaoAppAdminKey;
    @Value("${spring.security.oauth2.client.provider.kakao.delete-user-url}")
    private String kakaoDeleteAccountUrl;

    public Long withdraw(SessionUserInfo userInfo) {
        sendDeleteRequestToOAuth2(userInfo);
        memberRepository.deleteById(userInfo.getId());
        return userInfo.getId();
    }

    private void sendDeleteRequestToOAuth2(SessionUserInfo userInfo) {
        if (SocialProviderType.KAKAO == userInfo.getSocialProvider()) {
            deleteKakaoAccount(userInfo);
        } else if (SocialProviderType.GITHUB == userInfo.getSocialProvider()) {
            return ;
        } else {
            throw new ApiException(AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
        }
    }

    private void deleteKakaoAccount(SessionUserInfo userInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "KakaoAK " + kakaoAppAdminKey);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("target_id_type", "user_id");
        requestBody.add("target_id", userInfo.getOAuthId());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoDeleteAccountUrl);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
    }
}
