package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;

	@Override
	public Page<Favorite> findList(Long memberId, Pageable pageable) {
		return favoriteRepository.findFavoriteByMemberId(memberId, pageable);
	}

	@Override
	public Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId) {
		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId);
	}

	@Override
	public Optional<Favorite> findById(Long favoriteId) {
		return favoriteRepository.findById(favoriteId);
	}

	@Override
	public Favorite save(Favorite favorite) {
		return favoriteRepository.save(favorite);
	}
}

