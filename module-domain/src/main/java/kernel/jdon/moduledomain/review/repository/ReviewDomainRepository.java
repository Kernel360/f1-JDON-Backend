package kernel.jdon.moduledomain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.review.domain.Review;

public interface ReviewDomainRepository extends JpaRepository<Review, Long> {
}
