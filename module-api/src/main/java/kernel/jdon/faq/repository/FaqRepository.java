package kernel.jdon.faq.repository;

import org.springframework.stereotype.Repository;

import kernel.jdon.moduledomain.faq.repository.FaqDomainRepository;

@Repository("legacyFaqRepository")
public interface FaqRepository extends FaqDomainRepository {
}
