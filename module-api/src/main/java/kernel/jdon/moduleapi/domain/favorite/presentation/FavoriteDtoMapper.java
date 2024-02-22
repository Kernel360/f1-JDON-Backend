package kernel.jdon.moduleapi.domain.favorite.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FavoriteDtoMapper {
	FavoriteDto.FindPageResponse of(FavoriteInfo.FindPageResponse info);

	FavoriteCommand.UpdateRequest of(FavoriteDto.UpdateRequest request);

	FavoriteDto.UpdateResponse of(FavoriteInfo.UpdateResponse info);

	@Mappings({
		@Mapping(source = "inflearnCourse.id", target = "lectureId"),
		@Mapping(source = "inflearnCourse.title", target = "title"),
		@Mapping(source = "inflearnCourse.lectureUrl", target = "lectureUrl"),
		@Mapping(source = "inflearnCourse.imageUrl", target = "imageUrl"),
		@Mapping(source = "inflearnCourse.instructor", target = "instructor"),
		@Mapping(source = "inflearnCourse.studentCount", target = "studentCount"),
		@Mapping(source = "inflearnCourse.price", target = "price")
	})
	FavoriteInfo.FindResponse of(Favorite favorite);
}
