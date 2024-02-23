package kernel.jdon.moduleapi.domain.favorite.core;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteReader {
	Page<Favorite> findList(final Long memberId, Pageable pageable);

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId);

	Optional<Favorite> findById(Long favoriteId);

	Favorite save(Favorite favorite);
}
