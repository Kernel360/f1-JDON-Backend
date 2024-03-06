package kernel.jdon.moduleapi.domain.skill.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.skill.domain.BackendSkillType;
import kernel.jdon.moduledomain.skill.domain.FrontendSkillType;
import kernel.jdon.moduledomain.skill.domain.SkillType;

@Component
public class SkillKeywordManager {

	/**
	 * 입력 받은 문자열에 대한 모든 연관 용어담은 문자열 List로 반환하는 메서드
	 */
	public List<String> getAllKeywordAssociatedTermsByKeyword(String keyword) {
		List<String> combinedTermList = new ArrayList<>();
		combinedTermList.addAll(getAllKeywordAssociatedTermsBySkillType(BackendSkillType.values(), keyword));
		combinedTermList.addAll(getAllKeywordAssociatedTermsBySkillType(FrontendSkillType.values(), keyword));

		return combinedTermList.stream()
			.distinct()
			.toList();
	}

	/**
	 * 입력 받은 SkillType의 상수 배열에 입력받은 keyword가 포함되어 있는
	 * SkillType 상수의 모든 내부값 문자열을 리스트로 담아 반환하는 메서드
	 */
	private List<String> getAllKeywordAssociatedTermsBySkillType(SkillType[] skillTypeList, String keyword) {
		List<String> termList = new ArrayList<>();
		for (SkillType skillType : skillTypeList) {
			if (skillType.containsKeyword(keyword)) {
				termList.addAll(skillType.getKeywordAssociatedList(skillType));
				break;
			}
		}
		return termList;
	}
}
