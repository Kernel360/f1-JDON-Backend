package kernel.jdon.moduleapi.domain.jd.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum JdSortTypeCondition {
	LATEST("latest"),
	REVIEW("review");

	private final String sortCondition;

	JdSortTypeCondition(String sortCondition) {
		this.sortCondition = sortCondition;
	}

	public static JdSortTypeCondition of(String sortCondition) {
		if (!hasText(sortCondition))
			return LATEST;
		return Arrays.stream(values())
			.filter(name -> name.sortCondition.equals(sortCondition))
			.findFirst()
			.orElseThrow();
	}
}
