package kernel.jdon.favorite.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteRepository extends FavoriteDomainRepository {

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long inflearnId);

	Page<Favorite> findFavoriteByMemberId(Long memberId, Pageable pageable);

}
