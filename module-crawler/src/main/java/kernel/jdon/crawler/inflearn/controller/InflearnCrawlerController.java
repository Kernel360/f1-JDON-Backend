package kernel.jdon.crawler.inflearn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.crawler.inflearn.service.CrawlerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InflearnCrawlerController {

	private final CrawlerService crawlerService;

	@GetMapping("/api/v1/crawler/inflearn")
	public ResponseEntity<Void> getInflearnData() {
		crawlerService.createCourseInfo(1);

		return ResponseEntity.noContent().build();
	}

}
