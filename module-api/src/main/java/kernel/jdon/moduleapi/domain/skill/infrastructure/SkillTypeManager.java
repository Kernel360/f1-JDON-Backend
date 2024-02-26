package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import kernel.jdon.skill.domain.BackendSkillType;
import kernel.jdon.skill.domain.FrontendSkillType;

public class SkillTypeManager {

	public static List<String> getAllKeywordAssociatedTerms(String keyword) {
		List<String> backendTerms = Arrays.stream(BackendSkillType.values())
			.filter(backendSkillType -> backendSkillType.getKeyword().equalsIgnoreCase(keyword)
				|| backendSkillType.getTranslation().equals(keyword)
				|| backendSkillType.getRelatedKeywords().contains(keyword))
			.flatMap(backendSkillType -> Stream.concat(
				Stream.of(backendSkillType.getKeyword(), backendSkillType.getTranslation()),
				backendSkillType.getRelatedKeywords().stream()))
			.distinct()
			.toList();

		List<String> frontendTerms = Arrays.stream(FrontendSkillType.values())
			.filter(frontendSkillType -> frontendSkillType.getKeyword().equalsIgnoreCase(keyword)
				|| frontendSkillType.getTranslation().equals(keyword)
				|| frontendSkillType.getRelatedKeywords().contains(keyword))
			.flatMap(frontendSkillType -> Stream.concat(
				Stream.of(frontendSkillType.getKeyword(), frontendSkillType.getTranslation()),
				frontendSkillType.getRelatedKeywords().stream()))
			.distinct()
			.toList();

		List<String> combinedTerms = new ArrayList<>(backendTerms);
		combinedTerms.addAll(frontendTerms);
		return combinedTerms.stream()
			.distinct()
			.toList();
	}
}
