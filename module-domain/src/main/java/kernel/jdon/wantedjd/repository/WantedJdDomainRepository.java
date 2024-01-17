package kernel.jdon.wantedjd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.wantedjd.domain.WantedJd;

public interface WantedJdDomainRepository extends JpaRepository<WantedJd, Long> {
}
