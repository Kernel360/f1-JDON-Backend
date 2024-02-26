package kernel.jdon.moduleapi.domain.member.presentation;

import java.util.List;

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
	public static class UpdateMemberRequest {
		private String nickname;
		private String birth;
		private String gender;
		private Long jobCategoryId;
		private List<Long> skillList;
	}

	@Getter
	@Builder
	public static class UpdateMemberResponse {
		private Long memberId;
	}

}
