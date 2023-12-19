package kernel.jdon.domain.faq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.domain.faq.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
