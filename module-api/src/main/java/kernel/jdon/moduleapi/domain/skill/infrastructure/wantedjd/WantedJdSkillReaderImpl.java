package kernel.jdon.moduleapi.domain.skill.infrastructure.wantedjd;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.wantedjd.WantedJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WantedJdSkillReaderImpl implements WantedJdSkillReader {
	private final SkillRepository skillRepository;

	@Override
	public List<SkillInfo.FindJd> findWantedJdListBySkill(String keyword) {
		return skillRepository.findWantedJdListBySkill(keyword).stream()
			.map(wantedJd -> new SkillInfo.FindJd(wantedJd.getId(), wantedJd.getCompany(), wantedJd.getTitle(),
				wantedJd.getImageUrl(), wantedJd.getJdUrl()))
			.toList();
	}
}
