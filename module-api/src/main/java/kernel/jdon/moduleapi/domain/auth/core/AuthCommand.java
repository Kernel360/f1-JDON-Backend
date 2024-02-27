package kernel.jdon.moduleapi.domain.auth.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthCommand {
	@Getter
	@Builder
	public static class RegisterRequest {
		private final String encrypted;
		private final String hmac;
		private final String nickname;
		private final String birth;
		private final String gender;
		private final Long jobCategoryId;
		private final List<Long> skillList;
	}
}
