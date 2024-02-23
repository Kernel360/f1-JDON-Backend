package kernel.jdon.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetLoginStatusResponse {

	private Boolean isLoginUser;

	public static GetLoginStatusResponse of(Boolean isLoginUser) {
		return new GetLoginStatusResponse(isLoginUser);
	}
}
