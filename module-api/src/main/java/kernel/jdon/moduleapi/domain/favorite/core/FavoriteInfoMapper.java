package kernel.jdon.moduleapi.domain.favorite.core;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.favorite.infrastructure.FavoriteReaderInfo;
import kernel.jdon.moduledomain.favorite.domain.Favorite;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FavoriteInfoMapper {

    @Mappings({
        @Mapping(source = "inflearnCourse.id", target = "lectureId"),
        @Mapping(source = "inflearnCourse.title", target = "title"),
        @Mapping(source = "inflearnCourse.lectureUrl", target = "lectureUrl"),
        @Mapping(source = "inflearnCourse.imageUrl", target = "imageUrl"),
        @Mapping(source = "inflearnCourse.instructor", target = "instructor"),
        @Mapping(source = "inflearnCourse.studentCount", target = "studentCount"),
        @Mapping(source = "inflearnCourse.price", target = "price")
    })
    FavoriteReaderInfo.FindFavoriteListResponse of(Favorite favorite);
}
