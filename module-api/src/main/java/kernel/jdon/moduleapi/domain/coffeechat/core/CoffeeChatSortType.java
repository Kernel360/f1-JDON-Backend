package kernel.jdon.moduleapi.domain.coffeechat.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum CoffeeChatSortType {
	CREATED_DATE("createdDate"),
	VIEW_COUNT("viewCount");

	private final String webNaming;

	CoffeeChatSortType(String webNaming) {
		this.webNaming = webNaming;
	}

	public static CoffeeChatSortType of(String webNaming) {
		if (!hasText(webNaming)) {
			return null;
		}
		return Arrays.stream(values())
			.filter(name -> name.webNaming.equals(webNaming))
			.findFirst()
			.orElseThrow();
	}
}
