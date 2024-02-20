package kernel.jdon.moduleapi.domain.favorite.application;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;

public interface FavoriteReader {
	Page<FavoriteInfo.FindResponse> findList(final Long memberId, Pageable pageable);

}
