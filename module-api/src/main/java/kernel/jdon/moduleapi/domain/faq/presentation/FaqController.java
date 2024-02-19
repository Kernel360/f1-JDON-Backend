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
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.moduleapi.domain.faq.application.FaqFacade;
import kernel.jdon.moduleapi.domain.faq.core.FaqCommand;
import kernel.jdon.moduleapi.domain.faq.core.FaqInfo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {
	private final FaqFacade faqFacade;
	private final FaqDtoMapper faqDtoMapper;

	@PostMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse<FaqDto.CreateResponse>> save(@RequestBody @Valid FaqDto.CreateRequest request) {
		FaqCommand.CreateRequest command = faqDtoMapper.of(request);
		FaqInfo.CreateResponse info = faqFacade.create(command);
		FaqDto.CreateResponse response = faqDtoMapper.of(info);
		URI uri = URI.create("/api/v1/faqs/" + response.getFaqId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}

	@DeleteMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<CommonResponse<FaqDto.DeleteResponse>> remove(@PathVariable(name = "faqId") Long faqId) {
		FaqInfo.DeleteResponse info = faqFacade.delete(faqId);
		FaqDto.DeleteResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@PutMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse<FaqDto.UpdateResponse>> modify(@RequestBody @Valid FaqDto.UpdateRequest request) {
		FaqCommand.UpdateRequest command = faqDtoMapper.of(request);
		FaqInfo.UpdateResponse info = faqFacade.update(command);
		FaqDto.UpdateResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse<FaqDto.FindListResponse>> getList() {
		FaqInfo.FindListResponse info = faqFacade.findList();
		FaqDto.FindListResponse response = faqDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
