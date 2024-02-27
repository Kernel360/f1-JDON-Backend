package kernel.jdon.moduleapi.domain.jd.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomWantedJdRepository {
	Page<JdReaderInfo.FindWantedJd> findWantedJdList(Pageable pageable, String keyword);
}
