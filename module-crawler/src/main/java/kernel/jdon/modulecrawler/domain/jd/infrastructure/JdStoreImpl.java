package kernel.jdon.modulecrawler.domain.jd.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.modulecrawler.domain.jd.core.JdStore;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdStoreImpl implements JdStore {
    private final WantedJdRepository wantedJdRepository;

    @Override
    public WantedJd save(final WantedJd wantedJd) {
        return wantedJdRepository.save(wantedJd);
    }
}
