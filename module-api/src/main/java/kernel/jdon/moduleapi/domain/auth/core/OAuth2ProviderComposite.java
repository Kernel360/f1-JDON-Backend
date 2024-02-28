package kernel.jdon.moduleapi.domain.auth.core;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;

@Component
public class OAuth2ProviderComposite {
	private final Map<SocialProviderType, OAuth2Strategy> oauth2ProviderMap;

	public OAuth2ProviderComposite(Set<OAuth2Strategy> clients) {
		this.oauth2ProviderMap = clients.stream()
			.collect(toMap(OAuth2Strategy::getOAuth2ProviderType, identity()));
	}

	public OAuth2Strategy getOAuth2Strategy(SocialProviderType provider) {
		return Optional.ofNullable(oauth2ProviderMap.get(provider))
			.orElseThrow(AuthErrorCode.NOT_FOUND_PROVIDER::throwException);
	}

}
