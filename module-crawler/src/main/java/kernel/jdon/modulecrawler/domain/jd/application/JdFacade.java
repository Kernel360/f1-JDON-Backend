package kernel.jdon.modulecrawler.domain.jd.application;

import org.springframework.stereotype.Service;

import kernel.jdon.modulecrawler.domain.jd.core.JdService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdFacade {
    private final JdService jdService;

    public void scrapeWantedJd() {
        jdService.scrapeWantedJd();
    }
}
