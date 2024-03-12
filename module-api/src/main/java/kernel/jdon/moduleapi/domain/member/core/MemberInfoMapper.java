package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduledomain.member.domain.Member;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface MemberInfoMapper {

    @Mapping(source = "member.email", target = "email")
    @Mapping(source = "member.nickname", target = "nickname")
    @Mapping(source = "member.birth", target = "birth")
    @Mapping(expression = "java(member.getGender().getGender())", target = "gender")
    @Mapping(expression = "java(member.getJobCategory().getId())", target = "jobCategoryId")
    MemberInfo.FindMemberResponse of(Member member, List<Long> skillList);

}
