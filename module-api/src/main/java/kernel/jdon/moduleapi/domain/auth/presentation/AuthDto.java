package kernel.jdon.moduleapi.domain.auth.presentation;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kernel.jdon.moduleapi.global.annotation.validate.Gender;
import kernel.jdon.moduleapi.global.annotation.validate.IsPastDate;
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
		@NotBlank(message = "암호화 문자열이 필요합니다.")
		private String encrypted;
		@NotBlank(message = "hmac 값이 필요합니다.")
		private String hmac;
		@NotBlank(message = "닉네임은 필수 입력값입니다.")
		private String nickname;
		@IsPastDate
		private String birth;
		@NotBlank(message = "성별은 필수 입력값입니다.")
		@Gender
		private String gender;
		@Min(1)
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
