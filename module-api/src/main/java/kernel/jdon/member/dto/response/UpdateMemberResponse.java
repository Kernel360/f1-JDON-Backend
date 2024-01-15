package kernel.jdon.member.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberResponse {

	private Long memberId;

	public static UpdateMemberResponse of(Long memberId) {
		return new UpdateMemberResponse(memberId);
	}
}