package kernel.jdon.moduleapi.domain.auth.infrastructure;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kernel.jdon.moduleapi.domain.auth.core.OAuth2Strategy;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.global.config.auth.properties.WithdrawProperties;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2StrategyImpl implements OAuth2Strategy {
    private final WithdrawProperties withdrawProperties;

    @Override
    public SocialProviderType getOAuth2ProviderType() {
        return SocialProviderType.KAKAO;
    }

    @Override
    public SessionUserInfo getUserInfo(final OAuth2User user) {
        final Map<String, Object> attributes = user.getAttributes();
        final String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");
        isEmailExist(email);
        final String oAuthId = String.valueOf(attributes.get("id"));

        return SessionUserInfo.of(email, oAuthId, SocialProviderType.KAKAO);
    }

    @Override
    public boolean unlinkOAuth2Account(final MemberCommand.WithdrawRequest command) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "KakaoAK " + withdrawProperties.getAppAdminKey());
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("target_id_type", "user_id");
        requestBody.add("target_id", command.getOauthId());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(withdrawProperties.getDeleteUserUrl());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
            .exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);

        boolean success = false;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            success = true;
        }
        return success;
    }
}
