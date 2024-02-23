package kernel.jdon.moduledomain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.favorite.domain.Favorite;

public interface FavoriteDomainRepository extends JpaRepository<Favorite, Long> {
}
