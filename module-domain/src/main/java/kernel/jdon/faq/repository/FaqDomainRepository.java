package kernel.jdon.faq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.faq.domain.Faq;

public interface FaqDomainRepository extends JpaRepository<Faq, Long> {
}
