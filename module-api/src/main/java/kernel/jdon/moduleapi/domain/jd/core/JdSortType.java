package kernel.jdon.moduleapi.domain.jd.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum JdSortType {
    LATEST("latest"),
    REVIEW("review");
	
    private final String sortCondition;

    JdSortType(String sortCondition) {
        this.sortCondition = sortCondition;
    }

    public static JdSortType of(String sortCondition) {
        if (!hasText(sortCondition)) {
            return LATEST;
        }
        return Arrays.stream(values())
            .filter(name -> name.sortCondition.equals(sortCondition))
            .findFirst()
            .orElse(LATEST);
    }
}
