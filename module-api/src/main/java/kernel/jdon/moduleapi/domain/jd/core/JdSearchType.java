package kernel.jdon.moduleapi.domain.jd.core;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;

public enum JdSearchType {
    COMPANY("company"),
    TITLE("title");

    private final String searchCondition;

    JdSearchType(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public static JdSearchType of(String searchCondition) {
        if (!hasText(searchCondition)) {
            return TITLE;
        }
        return Arrays.stream(values())
            .filter(name -> name.searchCondition.equals(searchCondition))
            .findFirst()
            .orElse(TITLE);
    }
}
