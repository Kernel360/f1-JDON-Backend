package kernel.jdon.moduleapi.domain.auth.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthInfo {
	@Getter
	@Builder
	public static class RegisterResponse {
		private final Long memberId;

		public static RegisterResponse of(final Long memberId) {
			return new RegisterResponse(memberId);
		}
	}
}
