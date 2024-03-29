package kernel.jdon.moduleapi.domain.skill.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SkillDtoMapper {
    SkillDto.FindHotSkillListResponse of(SkillInfo.FindHotSkillListResponse info);

    SkillDto.FindMemberSkillListResponse of(SkillInfo.FindMemberSkillListResponse info);

    SkillDto.FindJobCategorySkillListResponse of(SkillInfo.FindJobCategorySkillListResponse info);

    SkillDto.FindDataListBySkillResponse of(SkillInfo.FindDataListBySkillResponse info);

}
