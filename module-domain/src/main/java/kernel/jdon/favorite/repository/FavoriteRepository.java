package kernel.jdon.favorite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long inflearnId);

}
