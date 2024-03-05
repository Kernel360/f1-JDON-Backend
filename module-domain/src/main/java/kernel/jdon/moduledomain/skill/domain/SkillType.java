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

	/**
	 * 입력받은 구현체의 내부 상수의 값들을 문자열 리스트에 담아 반환하는 메서드
	 */
	default List<String> getKeywordAssociatedList(SkillType skillType) {
		List<String> associatedList = new ArrayList<>();
		associatedList.add(skillType.getKeyword());
		associatedList.add(skillType.getTranslation());
		associatedList.addAll(skillType.getRelatedKeywords());

		return associatedList;
	}

	/**
	 * 입력받은 문자열이 구현체의 상수에 포함되어 있는지 확인하는 메서드
	 */
	default boolean containsKeyword(String keyword) {
		return getKeywordAssociatedList(this).stream()
			.anyMatch(term -> term.equalsIgnoreCase(keyword));
	}
}
