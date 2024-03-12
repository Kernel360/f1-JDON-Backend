package kernel.jdon.moduledomain.member.domain;

import java.util.Arrays;

public enum SocialProviderType {
    KAKAO("kakao"),
    GITHUB("github");

    private final String providerName;

    SocialProviderType(String providerName) {
        this.providerName = providerName;
    }

    public static SocialProviderType ofType(String providerName) {
        return Arrays.stream(SocialProviderType.values())
            .filter(e -> e.getProviderName().equals(providerName))
            .findAny()
            .orElseThrow(() -> null);
    }

    public String getProviderName() {
        return providerName;
    }
}
