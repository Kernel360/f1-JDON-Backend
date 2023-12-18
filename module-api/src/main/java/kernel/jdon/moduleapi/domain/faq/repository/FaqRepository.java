package kernel.jdon.moduleapi.domain.faq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	List<Faq> findAll();
}
