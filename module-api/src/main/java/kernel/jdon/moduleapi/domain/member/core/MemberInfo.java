package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfo {

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
}
