package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface JdInfoMapper {
    @Mapping(source = "wantedJd.id", target = "id")
    @Mapping(source = "wantedJd.title", target = "title")
    @Mapping(source = "wantedJd.companyName", target = "company")
    @Mapping(source = "wantedJd.imageUrl", target = "imageUrl")
    @Mapping(source = "wantedJd.detailUrl", target = "jdUrl")
    @Mapping(source = "wantedJd.requirements", target = "requirements")
    @Mapping(source = "wantedJd.mainTasks", target = "mainTasks")
    @Mapping(source = "wantedJd.intro", target = "intro")
    @Mapping(source = "wantedJd.benefits", target = "benefits")
    @Mapping(source = "wantedJd.preferredPoints", target = "preferredPoints")
    @Mapping(source = "skillList", target = "skillList")
    @Mapping(expression = "java(wantedJd.getReviewList().size())", target = "reviewCount")
    @Mapping(expression = "java(wantedJd.getJobCategory().getName())", target = "jobCategoryName")
    JdInfo.FindWantedJdResponse of(WantedJd wantedJd, List<JdInfo.FindSkill> skillList);
}
