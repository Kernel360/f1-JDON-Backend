package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.List;

public interface CustomSkillRepository {
	List<SkillReaderInfo.FindHotSkill> findHotSkillList();

	List<SkillReaderInfo.FindMemberSkill> findMemberSkillList(final Long memberId);

	List<SkillReaderInfo.FindWantedJd> findWantedJdListBySkill(final String keyword);

	List<SkillReaderInfo.FindInflearnLecture> findInflearnLectureListBySkill(final String keyword, final Long memberId);
}
