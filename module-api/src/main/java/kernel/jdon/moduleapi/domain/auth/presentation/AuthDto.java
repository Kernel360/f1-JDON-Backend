package kernel.jdon.moduleapi.domain.auth.presentation;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthDto {
	@Getter
	@Builder
	public static class RegisterRequest {
		private String encrypted;
		private String hmac;
		private String nickname;
		private String birth;
		private String gender;
		private Long jobCategoryId;
		private List<Long> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class RegisterResponse {
		private Long memberId;
	}

	@Getter
	@AllArgsConstructor
	public static class AuthenticateResponse {
		private boolean isLoginUser;
		private Long memberId;

		public static AuthenticateResponse of(boolean isLoginUser, Long memberId) {
			return new AuthenticateResponse(isLoginUser, memberId);
		}
	}

}
