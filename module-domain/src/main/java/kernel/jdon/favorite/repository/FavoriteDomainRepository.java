package kernel.jdon.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.favorite.domain.Favorite;

public interface FavoriteDomainRepository extends JpaRepository<Favorite, Long> {
}
