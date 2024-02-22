package kernel.jdon.moduleapi.domain.skill.infrastructure.inflearnjd;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InflearnJdSkillReaderImpl implements InflearnJdSkillReader {
	private final SkillRepository skillRepository;

	@Override
	public List<SkillInfo.FindLecture> findInflearnLectureListBySkill(final String keyword, final Long memberId) {
		return skillRepository.findInflearnLectureListBySkill(keyword, memberId).stream()
			.map(
				inflearnLecture -> new SkillInfo.FindLecture(inflearnLecture.getLectureId(), inflearnLecture.getTitle(),
					inflearnLecture.getLectureUrl(), inflearnLecture.getImageUrl(), inflearnLecture.getInstructor(),
					inflearnLecture.getStudentCount(), inflearnLecture.getPrice(), inflearnLecture.getIsFavorite()))
			.toList();
	}
}
