package kernel.jdon.auth;

import java.io.Serializable;

import kernel.jdon.member.domain.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemporaryUser implements Serializable {
	private String email;
	private SocialProviderType socialProvider;
}
