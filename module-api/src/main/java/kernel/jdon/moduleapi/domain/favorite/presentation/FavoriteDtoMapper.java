package kernel.jdon.moduleapi.domain.favorite.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

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
}
