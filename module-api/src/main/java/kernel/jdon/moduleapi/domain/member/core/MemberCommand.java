package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCommand {

	@Getter
	@Builder
	public static class UpdateMemberRequest {
		private final String nickname;
		private final String birth;
		private final String gender;
		private final Long jobCategoryId;
		private final List<Long> skillList;
	}

	@Getter
	@Builder
	public static class NicknameDuplicateRequest {
		private String nickname;
	}
}
