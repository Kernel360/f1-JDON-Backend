package kernel.jdon.moduleapi.domain.member.presentation;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kernel.jdon.moduleapi.global.annotation.validate.Gender;
import kernel.jdon.moduleapi.global.annotation.validate.IsPastDate;
import kernel.jdon.moduleapi.global.annotation.validate.IsValidNickname;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

	@Getter
	@Builder
	public static class FindMemberResponse {
		private String email;
		private String nickname;
		private String birth;
		private String gender;
		private Long jobCategoryId;
		private List<Long> skillList;
	}

	@Getter
	@Builder
	public static class UpdateMemberRequest {
		@NotBlank(message = "닉네임은 필수 입력값입니다.")
		@IsValidNickname
		private String nickname;
		@IsPastDate()
		private String birth;
		@NotBlank(message = "성별은 필수 입력값입니다.")
		@Gender
		private String gender;
		@Min(1)
		private Long jobCategoryId;
		private List<Long> skillList;
	}

	@Getter
	@Builder
	public static class UpdateMemberResponse {
		private Long memberId;
	}

	@Getter
	@Setter
	public static class NicknameDuplicateRequest {
		@NotBlank(message = "증복 확인하려는 닉네임을 작성해주세요.")
		@IsValidNickname
		private String nickname;
	}

	@Getter
	@Builder
	public static class RegisterRequest {
		@NotBlank(message = "암호화 문자열이 필요합니다.")
		private String encrypted;
		@NotBlank(message = "hmac 값이 필요합니다.")
		private String hmac;
		@NotBlank(message = "닉네임은 필수 입력값입니다.")
		@IsValidNickname
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

		public static MemberDto.AuthenticateResponse of(final boolean isLoginUser, final Long memberId) {
			return new MemberDto.AuthenticateResponse(isLoginUser, memberId);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class WithdrawResponse {
		private Long memberId;

		public static WithdrawResponse of(final Long memberId) {
			return new WithdrawResponse(memberId);
		}
	}

}
