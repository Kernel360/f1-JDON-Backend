package kernel.jdon.moduleapi.domain.member.presentation;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kernel.jdon.moduleapi.global.annotation.validate.Gender;
import kernel.jdon.moduleapi.global.annotation.validate.isPastDate;
import lombok.Builder;
import lombok.Getter;

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
		private String nickname;
		@isPastDate()
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

}
