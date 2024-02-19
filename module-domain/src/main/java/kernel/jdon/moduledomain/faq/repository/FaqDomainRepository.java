package kernel.jdon.moduledomain.faq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.faq.domain.Faq;

public interface FaqDomainRepository extends JpaRepository<Faq, Long> {
}
