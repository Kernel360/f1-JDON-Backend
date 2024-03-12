package kernel.jdon.moduleapi.domain.skill.infrastructure;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SkillReaderInfoMapper {
    SkillInfo.FindHotSkill of(SkillReaderInfo.FindHotSkill info);

    @Mapping(source = "skillId", target = "id")
    SkillInfo.FindMemberSkill of(SkillReaderInfo.FindMemberSkill info);

    SkillInfo.FindJd of(SkillReaderInfo.FindWantedJd info);

    SkillInfo.FindLecture of(SkillReaderInfo.FindInflearnLecture info);
}
