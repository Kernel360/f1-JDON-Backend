package kernel.jdon.moduleapi.domain.faq.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.faq.dto.request.CreateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.request.UpdateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.CreateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.DeleteFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindAllFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.UpdateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.service.FaqService;
import kernel.jdon.moduleapi.global.common.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {

	private final FaqService faqService;

	@GetMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<FindFaqResponse> get(@PathVariable(name = "faqId") Long faqId) {
		FindFaqResponse findFaqResponse = faqService.find(faqId);

		return ResponseEntity.ok().body(findFaqResponse);
	}

	@PostMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse> save(@RequestBody CreateFaqRequest createFaqRequest) {
		CreateFaqResponse createFaqResponse = faqService.create(createFaqRequest);
		URI uri = URI.create("/api/v1/faqs/" + createFaqResponse.getFaqId());

		return ResponseEntity.created(uri).body(CommonResponse.of(createFaqResponse));
	}

	@DeleteMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<CommonResponse> remove(@PathVariable(name = "faqId") Long faqId) {
		DeleteFaqResponse deleteFaqResponse = faqService.delete(faqId);

		return ResponseEntity.ok(CommonResponse.of(deleteFaqResponse));
	}

	@PutMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse> modify(@RequestBody UpdateFaqRequest updateFaqRequest) {
		UpdateFaqResponse updateFaqResponse = faqService.update(updateFaqRequest);

		return ResponseEntity.ok().body(CommonResponse.of(updateFaqResponse));
	}

	@GetMapping("/api/v1/faqs")
	public ResponseEntity<FindAllFaqResponse> getAllFaqs() {
		FindAllFaqResponse findAllFaqResponseList = faqService.findAll();

		return ResponseEntity.ok().body(findAllFaqResponseList);
	}

}
