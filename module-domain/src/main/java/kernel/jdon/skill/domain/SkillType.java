package kernel.jdon.skill.domain;

import java.util.List;

public interface SkillType {
	static String getOrderKeyword() {
		return "기타";
	}
	
	String getKeyword();

	String getTranslation();

	List<String> getRelatedKeywords();
}
