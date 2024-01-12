package kernel.jdon.crawler.wanted.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.crawler.wanted.service.WantedCrawlerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WantedCrawlerController {

	private final WantedCrawlerService wantedCrawlerService;

	@GetMapping("/api/v1/crawler/wanted")
	public ResponseEntity<Void> getWantedData() throws InterruptedException {
		wantedCrawlerService.fetchJd();
		return ResponseEntity.noContent().build();
	}
}
