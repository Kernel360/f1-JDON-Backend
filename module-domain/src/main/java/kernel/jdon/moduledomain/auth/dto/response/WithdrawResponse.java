package kernel.jdon.moduledomain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawResponse {
	private Long memberId;

	public static WithdrawResponse of(Long memberId) {
		return new WithdrawResponse(memberId);
	}
}
