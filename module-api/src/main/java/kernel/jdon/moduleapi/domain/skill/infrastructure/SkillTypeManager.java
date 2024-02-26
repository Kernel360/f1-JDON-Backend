package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import kernel.jdon.skill.domain.BackendSkillType;
import kernel.jdon.skill.domain.FrontendSkillType;

public class SkillTypeManager {

	public static List<String> getAllKeywordAssociatedTerms() {
		List<String> backendTerms = Arrays.stream(BackendSkillType.values())
			.flatMap(backendSkillType -> Stream.concat(
				Stream.of(backendSkillType.getKeyword(), backendSkillType.getTranslation()),
				backendSkillType.getRelatedKeywords().stream()))
			.toList();

		List<String> frontendTerms = Arrays.stream(FrontendSkillType.values())
			.flatMap(frontendSkillType -> Stream.concat(
				Stream.of(frontendSkillType.getKeyword(), frontendSkillType.getTranslation()),
				frontendSkillType.getRelatedKeywords().stream()))
			.toList();
		
		return Stream.concat(backendTerms.stream(), frontendTerms.stream())
			.distinct()
			.toList();
	}
}
