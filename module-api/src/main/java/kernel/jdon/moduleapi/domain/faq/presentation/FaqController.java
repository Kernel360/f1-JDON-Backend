package kernel.jdon.moduleapi.domain.faq.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.moduleapi.domain.faq.application.FaqFacade;
import kernel.jdon.moduleapi.domain.faq.core.FaqCommand;
import kernel.jdon.moduleapi.domain.faq.core.FaqInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {
	private final FaqFacade faqFacade;
	private final FaqDtoMapper faqDtoMapper;

	@PostMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse<FaqDto.CreateFaqResponse>> create(
		@RequestBody @Valid final FaqDto.CreateFaqRequest request) {
		final FaqCommand.CreateFaqRequest command = faqDtoMapper.of(request);
		final FaqInfo.CreateFaqResponse info = faqFacade.create(command);
		final FaqDto.CreateFaqResponse response = faqDtoMapper.of(info);
		final URI uri = URI.create("/api/v1/faqs/" + response.getFaqId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}

	@DeleteMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<CommonResponse<FaqDto.DeleteFaqResponse>> remove(
		@PathVariable(name = "faqId") final Long faqId) {
		FaqInfo.DeleteFaqResponse info = faqFacade.remove(faqId);
		FaqDto.DeleteFaqResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@PutMapping("/api/v1/faqs/{id}")
	public ResponseEntity<CommonResponse<FaqDto.UpdateFaqResponse>> modify(
		@PathVariable(name = "id") final Long faqId,
		@RequestBody @Valid final FaqDto.UpdateFaqRequest request) {
		final FaqCommand.UpdateFaqRequest command = faqDtoMapper.of(request, faqId);
		final FaqInfo.UpdateFaqResponse info = faqFacade.modify(command);
		final FaqDto.UpdateFaqResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse<FaqDto.FindFaqListResponse>> getFaqList() {
		final FaqInfo.FindFaqListResponse info = faqFacade.getFaqList();
		final FaqDto.FindFaqListResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
