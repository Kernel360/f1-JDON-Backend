package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteReader {
	Page<Favorite> findList(final Long memberId, Pageable pageable);

}
