package kernel.jdon.faq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.faq.domain.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
