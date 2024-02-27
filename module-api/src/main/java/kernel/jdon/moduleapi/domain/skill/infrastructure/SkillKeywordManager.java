package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.skill.domain.BackendSkillType;
import kernel.jdon.skill.domain.FrontendSkillType;
import kernel.jdon.skill.domain.SkillType;

@Component
public class SkillKeywordManager {

	public List<String> getAllKeywordAssociatedTerms(String keyword) {

		List<String> combinedTermList = new ArrayList<>();

		combinedTermList.addAll(getAllKeywordAssociatedTerms(BackendSkillType.values(), keyword));
		combinedTermList.addAll(getAllKeywordAssociatedTerms(FrontendSkillType.values(), keyword));

		return combinedTermList.stream()
			.distinct()
			.toList();
	}

	private List<String> getAllKeywordAssociatedTerms(SkillType[] skillTypeList, String keyword) {
		List<String> termList = new ArrayList<>();
		for (SkillType skillType : skillTypeList) {
			if (skillType.containsKeyword(keyword)) {
				termList.addAll(skillType.getKeywordAssociatedList(skillType));
			}
		}
		return termList;
	}
}
