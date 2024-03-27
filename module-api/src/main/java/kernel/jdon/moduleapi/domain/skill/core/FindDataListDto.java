package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindDataListDto {
    List<SkillInfo.FindLecture> findLectureList;
    List<SkillInfo.FindJd> findJdList;
}

