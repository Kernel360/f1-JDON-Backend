package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.ArrayList;
import java.util.List;

import kernel.jdon.skill.domain.BackendSkillType;
import kernel.jdon.skill.domain.FrontendSkillType;

public class SkillTypeManager {

	public static List<String> getAllKeywordAssociatedTerms(String keyword) {

		List<String> backendTermList = BackendSkillType.getAllKeywordAssociatedTerms(keyword);
		List<String> frontendTermList = FrontendSkillType.getAllKeywordAssociatedTerms(keyword);
		List<String> combinedTermList = new ArrayList<>(backendTermList);
		combinedTermList.addAll(frontendTermList);
		
		return combinedTermList.stream()
			.distinct()
			.toList();
	}
}
