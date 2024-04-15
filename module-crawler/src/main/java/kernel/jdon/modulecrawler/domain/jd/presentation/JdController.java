package kernel.jdon.modulecrawler.domain.jd.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.modulecommon.dto.response.CommonResponse;
import kernel.jdon.modulecrawler.domain.jd.application.JdFacade;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JdController {
    private final JdFacade jdFacade;

    @PostMapping("/api/v1/jds/scrape/wanted")
    public ResponseEntity<CommonResponse<Void>> scrapeWantedJd() {
        jdFacade.scrapeWantedJd();

        return ResponseEntity.noContent().build();
    }
}
