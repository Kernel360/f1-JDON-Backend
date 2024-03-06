package kernel.jdon.moduleapi.domain.jd.presentation;

import kernel.jdon.moduleapi.domain.jd.core.JdSearchTypeCondition;
import kernel.jdon.moduleapi.domain.jd.core.JdSortTypeCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JdCondition {
	private final String skill;
	private final Long jobCategory;
	private final JdSearchTypeCondition keywordType;
	private final String keyword;
	private final JdSortTypeCondition sort;

	public static JdCondition of(final String skill, final Long jobCategory,
		final JdSearchTypeCondition keywordType, final String keyword,
		final JdSortTypeCondition sort) {
		return new JdCondition(skill, jobCategory, keywordType, keyword, sort);
	}
}
