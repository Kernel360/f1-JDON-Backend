package kernel.jdon.moduleapi.domain.skill.infrastructure.inflearnjd;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillReaderInfoMapper;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InflearnJdSkillReaderImpl implements InflearnJdSkillReader {
    private final SkillRepository skillRepository;
    private final SkillReaderInfoMapper skillReaderInfoMapper;

    @Override
    public List<SkillInfo.FindLecture> findInflearnLectureListBySkill(final List<String> originSkillKeywordList,
        final Long memberId) {
        return skillRepository.findInflearnLectureListBySkill(originSkillKeywordList, memberId).stream()
            .map(skillReaderInfoMapper::of)
            .toList();
    }
}
