package kernel.jdon.moduleapi.global.validate;

import java.util.Arrays;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InvalidNickname {
	ADMIN("admin"),
	ADMINISTER("administer"),
	어드민("어드민"),
	관리자("관리자");

	private final String keyword;

	public static boolean isInvalid(String keyword) {
		InvalidNickname findInvalidKeyword = Arrays.stream(InvalidNickname.values())
			.filter(e -> keyword.toUpperCase().contains(e.getKeyword().toUpperCase()))
			.findAny()
			.orElse(null);

		return Objects.isNull(findInvalidKeyword);
	}

	public String getKeyword() {
		return keyword;
	}
}
