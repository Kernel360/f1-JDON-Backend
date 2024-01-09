package kernel.jdon.member.domain;

public enum SocialProviderType {
	KAKAO("kakao"),
	GITHUB("github");

	private final String provider;

	SocialProviderType(String provider) {
		this.provider = provider;
	}

	public String getProvider() {
		return provider;
	}
}
