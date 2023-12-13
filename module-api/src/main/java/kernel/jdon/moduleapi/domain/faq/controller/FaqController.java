package kernel.jdon.moduleapi.domain.faq.controller;

import kernel.jdon.moduleapi.domain.faq.dto.request.ModifyFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.ModifyFaqResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.faq.dto.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.service.FaqService;
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

	@DeleteMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<Long> remove(@PathVariable(name = "faqId") Long faqId) {
		faqService.delete(faqId);

		return ResponseEntity.ok(faqId);
	}


	@PutMapping("/api/v1/faqs")
	public ResponseEntity<ModifyFaqResponse> modify(@RequestBody ModifyFaqRequest modifyFaqRequest) {
		ModifyFaqResponse modifyFaqResponse = faqService.update(modifyFaqRequest);

		return ResponseEntity.ok().body(modifyFaqResponse);
	}
}
