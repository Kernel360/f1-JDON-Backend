package kernel.jdon.modulecrawler.legacy.inflearn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.modulecrawler.legacy.inflearn.service.CrawlerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InflearnCrawlerController {

    private final CrawlerService crawlerService;

    @GetMapping("/api/v1/crawler/inflearn")
    public ResponseEntity<Void> getInflearnData() {
        crawlerService.createCourseInfo();

        return ResponseEntity.noContent().build();
    }

}
