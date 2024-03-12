package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.moduledomain.favorite.domain.Favorite;

public interface FavoriteFactory {
    Favorite create(Long memberId, Long lectureId);

    Favorite delete(Long memberId, Long lectureId);
}
