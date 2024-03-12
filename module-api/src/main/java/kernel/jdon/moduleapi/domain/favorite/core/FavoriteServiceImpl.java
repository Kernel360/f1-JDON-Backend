package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteReader favoriteReader;
    private final FavoriteFactory favoriteFactory;

    @Override
    @Transactional
    public FavoriteInfo.UpdateResponse create(final Long memberId, final Long lectureId) {
        final Favorite saveFavorite = favoriteFactory.create(memberId, lectureId);

        return new FavoriteInfo.UpdateResponse(saveFavorite.getInflearnCourse().getId());
    }

    @Override
    @Transactional
    public FavoriteInfo.UpdateResponse remove(final Long memberId, final Long lectureId) {
        Favorite deleteFavorite = favoriteFactory.delete(memberId, lectureId);

        return new FavoriteInfo.UpdateResponse(deleteFavorite.getInflearnCourse().getId());
    }

    @Override
    public FavoriteInfo.FindFavoriteListResponse getFavoriteList(Long memberId, PageInfoRequest pageInfoRequest) {
        final FavoriteInfo.FindFavoriteListResponse favoriteList = favoriteReader.findList(memberId, pageInfoRequest);

        return favoriteList;
    }
}
