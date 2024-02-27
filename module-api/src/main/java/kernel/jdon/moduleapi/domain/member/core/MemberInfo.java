package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfo {

	@Getter
	@Builder
	public static class FindMemberResponse {
		private final String email;
		private final String nickname;
		private final String birth;
		private final String gender;
		private final Long jobCategoryId;
		private final List<Long> skillList;
	}

	@Getter
	@AllArgsConstructor
	public static class UpdateMemberResponse {
		private final Long memberId;

		public static UpdateMemberResponse of(final Long memberId) {
			return new UpdateMemberResponse(memberId);
		}
	}
}
