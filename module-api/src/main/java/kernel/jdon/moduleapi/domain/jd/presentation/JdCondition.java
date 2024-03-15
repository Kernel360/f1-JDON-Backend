package kernel.jdon.moduleapi.domain.jd.presentation;

import kernel.jdon.moduleapi.domain.jd.core.JdSearchType;
import kernel.jdon.moduleapi.domain.jd.core.JdSortType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JdCondition {
    private final String skill;
    private final Long jobCategory;
    private final JdSearchType keywordType;
    private final String keyword;
    private final JdSortType sort;

    public static JdCondition of(final String skill, final Long jobCategory,
        final JdSearchType keywordType, final String keyword, final JdSortType sort) {
        return new JdCondition(skill, jobCategory, keywordType, keyword, sort);
    }
}
