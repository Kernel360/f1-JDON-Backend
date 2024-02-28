package kernel.jdon.moduleapi.global.util;

import java.util.Optional;

import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUserUtil {
	public static Long getSessionUserId(SessionUserInfo sessionUser) {
		return Optional.ofNullable(sessionUser)
			.map(session -> sessionUser.getId())
			.orElse(null);
	}
}
