package kernel.jdon.moduleapi.domain.jd.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum JdSearchTypeCondition {
	COMPANY("company"),
	TITLE("title");

	private final String searchCondition;

	JdSearchTypeCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public static JdSearchTypeCondition of(String searchCondition) {
		if (!hasText(searchCondition))
			return null;
		return Arrays.stream(values())
			.filter(name -> name.searchCondition.equals(searchCondition))
			.findFirst()
			.orElseThrow();
	}
}
