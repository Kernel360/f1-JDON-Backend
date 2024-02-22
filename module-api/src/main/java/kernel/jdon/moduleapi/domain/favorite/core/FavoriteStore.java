package kernel.jdon.moduleapi.domain.favorite.core;

public interface FavoriteStore {
	FavoriteInfo.UpdateResponse create(Long memberId, FavoriteCommand.UpdateRequest command);

	void delete(Long memberId, Long lectureId);
}
