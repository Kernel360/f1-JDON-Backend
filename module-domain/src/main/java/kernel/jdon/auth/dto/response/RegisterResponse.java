package kernel.jdon.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

	private Long memberId;

	public static RegisterResponse of(Long memberId) {
		return new RegisterResponse(memberId);
	}
}
