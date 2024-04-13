package kernel.jdon.modulecrawler.domain.jd.core;

import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

public interface JdStore {
    WantedJd save(WantedJd wantedJd);
}
