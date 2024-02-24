package kernel.jdon.moduleapi.domain.jd.core;

import kernel.jdon.wantedjd.domain.WantedJd;

public interface JdReader {
	WantedJd findWantedJd(final Long jdId);
}
