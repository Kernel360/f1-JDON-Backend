package kernel.jdon.moduleapi.domain.jd.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;

public interface CustomWantedJdRepository {
	Page<JdReaderInfo.FindWantedJd> findWantedJdList(Pageable pageable, JdCondition jdCondition);
}
