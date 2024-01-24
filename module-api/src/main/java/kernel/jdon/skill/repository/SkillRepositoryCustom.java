package kernel.jdon.skill.repository;

import java.util.List;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import kernel.jdon.skill.dto.object.FindWantedJdDto;
import kernel.jdon.skill.dto.object.FindLectureDto;

public interface SkillRepositoryCustom {
	List<FindHotSkillDto> findHotSkillList();
	List<FindMemberSkillDto> findMemberSkillList(Long memberId);
	List<FindWantedJdDto> findWantedJdListBySkill(String keyword);
	List<FindLectureDto> findInflearnLectureListBySkill(String keyword);
}
