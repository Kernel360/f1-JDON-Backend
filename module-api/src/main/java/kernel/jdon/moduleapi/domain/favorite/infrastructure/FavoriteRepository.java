package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.favorite.repository.FavoriteDomainRepository;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;

public interface FavoriteRepository extends FavoriteDomainRepository {

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long inflearnId);

	Page<FavoriteInfo.FindResponse> findFavoriteByMemberId(Long memberId, Pageable pageable);
}