package kernel.jdon.coffeechat.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
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

	@GetMapping("/v1/coffeechats/me/apply")
	public ResponseEntity<CommonResponse> getGuestCoffeeChatList() {
		List<FindCoffeeChatResponse> list = new ArrayList<>();
		for (long i = 10; i <= 20; i++) {
			FindCoffeeChatResponse response = FindCoffeeChatResponse.builder()
				.coffeeChatId(i)
				.nickname("김영한" + i)
				.job("backend")
				.title("커피챗제목" + i)
				.status("모집중")
				.meetDate(LocalDateTime.now())
				.createdDate(LocalDateTime.now())
				.currentRecruitCount(5L)
				.totalRecruitCount(i)
				.build();
			list.add(response);
		}

		return ResponseEntity.ok(CommonResponse.of(list));
	}

	// @GetMapping("/v1/coffeechats/me/open")
	// public ResponseEntity<CommonResponse> getHostCoffeeChatList() {
	//
	// }
	//
	// @GetMapping("/v1/coffeechats")
	// public ResponseEntity<CommonResponse> getCoffeeChatList() {
	//
	// }

}
