package kernel.jdon.auth.dto;

import kernel.jdon.member.domain.SocialProviderType;
import lombok.Getter;

@Getter
public class UserInfoFromOAuth2 {
    private String email;
    private String oAuthId;
    private SocialProviderType socialProvider;

    private UserInfoFromOAuth2(String email, String oAuthId, SocialProviderType socialProvider) {
        this.email = email;
        this.oAuthId = oAuthId;
        this.socialProvider = socialProvider;
    }

    public static UserInfoFromOAuth2 of(String email, String oAuthId, SocialProviderType socialProvider) {
        return new UserInfoFromOAuth2(email, oAuthId, socialProvider);
    }
}
