package kernel.jdon.moduleapi.domain.jobcategory.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface JobCategoryMapper {
    JobCategoryDto.FindJobGroupListResponse of(JobCategoryInfo.FindJobGroupListResponse info);
}
