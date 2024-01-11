package kernel.jdon.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveMemberResponse {

	private Long memberId;

	public static SaveMemberResponse of(Long memberId) {
		return new SaveMemberResponse(memberId);
	}
}
