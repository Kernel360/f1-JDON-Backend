package kernel.jdon.favorite.repository;

import java.util.Optional;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteRepository extends FavoriteDomainRepository {

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long inflearnId);

}
