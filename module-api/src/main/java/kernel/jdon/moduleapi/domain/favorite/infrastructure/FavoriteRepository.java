package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.moduledomain.favorite.domain.Favorite;
import kernel.jdon.moduledomain.favorite.repository.FavoriteDomainRepository;

public interface FavoriteRepository extends FavoriteDomainRepository {

    Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long inflearnId);

    Page<Favorite> findFavoriteByMemberId(Long memberId, Pageable pageable);
}