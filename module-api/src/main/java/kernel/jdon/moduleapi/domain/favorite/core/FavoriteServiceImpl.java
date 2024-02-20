package kernel.jdon.moduleapi.domain.favorite.core;

import java.awt.print.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.favorite.application.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteStore;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;

	@Override
	public FavoriteInfo.UpdateResponse update(Long memberId, FavoriteCommand.UpdateRequest command) {
		return null;
	}

	@Override
	public FavoriteInfo.FindResponse getList(Long memberId, Pageable pageable) {
		return null;
	}
}
