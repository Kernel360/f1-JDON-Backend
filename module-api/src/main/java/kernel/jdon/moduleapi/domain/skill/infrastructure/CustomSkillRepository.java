package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.List;

public interface CustomSkillRepository {
    List<SkillReaderInfo.FindHotSkill> findHotSkillList();

    List<SkillReaderInfo.FindMemberSkill> findMemberSkillList(Long memberId);

    List<SkillReaderInfo.FindWantedJd> findWantedJdListBySkill(String keyword);

    List<SkillReaderInfo.FindInflearnLecture> findInflearnLectureListBySkill(String keyword, Long memberId);
}
