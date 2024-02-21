package kernel.jdon.moduleapi.domain.skill.core.inflearnjd;

import java.util.List;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;

public interface InflearnJdSkillReader {
	List<SkillInfo.FindLecture> findInflearnLectureListBySkill(final String keyword,
		final Long memberId);
}
