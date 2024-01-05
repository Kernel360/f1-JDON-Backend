package kernel.jdon.crawler.inflearn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.crawler.inflearn.service.InflearnCrawlerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InflearnCrawlerController {

	private final InflearnCrawlerService inflearnCrawlerService;

	@GetMapping("/api/v1/crawler/inflearn")
	public ResponseEntity<Object> getInflearnData() {
		inflearnCrawlerService.fetchCourseInfo();

		return ResponseEntity.noContent().build();
	}

}
