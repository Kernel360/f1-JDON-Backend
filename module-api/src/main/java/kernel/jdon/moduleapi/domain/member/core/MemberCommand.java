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
		private String nickname;
		private String birth;
		private String gender;
		private Long jobCategoryId;
		private List<Long> skillList;
	}

}
