package kernel.jdon.moduledomain.skill.domain;

import java.util.ArrayList;
import java.util.List;

public interface SkillType {
	static String getOrderKeyword() {
		return "기타";
	}

	String getKeyword();

	String getTranslation();

	List<String> getRelatedKeywords();

	default List<String> getKeywordAssociatedList(SkillType skillType) {
		List<String> associatedList = new ArrayList<>();
		associatedList.add(skillType.getKeyword());
		associatedList.add(skillType.getTranslation());
		associatedList.addAll(skillType.getRelatedKeywords());

		return associatedList;
	}

	default boolean containsKeyword(String keyword) {
		return getKeywordAssociatedList(this).stream().anyMatch(term -> term.equalsIgnoreCase(keyword));
	}
}
