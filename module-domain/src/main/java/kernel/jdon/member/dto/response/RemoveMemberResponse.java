package kernel.jdon.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveMemberResponse {
	private Long memberId;

	public static RemoveMemberResponse of(Long memberId) {
		return new RemoveMemberResponse(memberId);
	}
}
