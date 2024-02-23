package kernel.jdon.moduleapi.domain.coffeechat.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum CoffeeChatSortCondition {
	CREATED_DATE("createdDate"),
	VIEW_COUNT("viewCount");

	private final String webNaming;

	CoffeeChatSortCondition(String webNaming) {
		this.webNaming = webNaming;
	}

	public static CoffeeChatSortCondition of(String webNaming) {
		if (!hasText(webNaming))
			return null;
		return Arrays.stream(values())
			.filter(name -> name.webNaming.equals(webNaming))
			.findFirst()
			.orElseThrow();
	}
}
