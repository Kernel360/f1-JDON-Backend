package kernel.jdon.auth.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import kernel.jdon.member.domain.SocialProviderType;
import lombok.Getter;

@Getter
public class JdonOAuth2User extends DefaultOAuth2User {

	private final String email;
	private final SocialProviderType socialProvider;

	/**
	 * Constructs a {@code DefaultOAuth2User} using the provided parameters.
	 * @param authorities the authorities granted to the user
	 * @param attributes the attributes about the user
	 * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
	 * {@link #getAttributes()}
	 * @param email
	 * @param socialProvider
	 */
	public JdonOAuth2User(Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes, String nameAttributeKey, String email,
		SocialProviderType socialProvider) {
		super(authorities, attributes, nameAttributeKey);
		this.email = email;
		this.socialProvider = socialProvider;
	}

	public String getSocialProviderType() {
		return socialProvider.getProviderName();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
