package kernel.jdon.coffeechat.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.service.CoffeeChatService;
import kernel.jdon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CoffeeChatController {

	private final CoffeeChatService coffeeChatService;

	@GetMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse> get(@PathVariable(name = "id") Long coffeeChatId) {

		return ResponseEntity.ok().body(CommonResponse.of(coffeeChatService.find(coffeeChatId)));
	}

	@PostMapping("/api/v1/coffeechats")
	public ResponseEntity<CommonResponse> save(@RequestBody CreateCoffeeChatRequest request) {
		CreateCoffeeChatResponse response = coffeeChatService.create(request);
		URI uri = URI.create("/v1/coffeechats/" + response.getCoffeeChatId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}
}
