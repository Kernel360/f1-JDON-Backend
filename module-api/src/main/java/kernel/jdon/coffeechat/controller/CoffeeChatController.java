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
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatListResponse;
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

	@GetMapping("/api/v1/coffeechats/guest")
	public ResponseEntity<CommonResponse> getGuestCoffeeChatList() {
		List<FindCoffeeChatListResponse> list = new ArrayList<>();
		for (long i = 10; i <= 20; i++) {
			FindCoffeeChatListResponse response = FindCoffeeChatListResponse.builder()
				.coffeeChatId(i)
				.nickname("김영한" + i)
				.job("backend")
				.title("주니어 백엔드 개발자를 대상으로 커피챗을 엽니다." + i)
				.status("모집중")
				.meetDate(LocalDateTime.now().plusMinutes(i))
				.createdDate(LocalDateTime.now())
				.currentRecruitCount(5L)
				.totalRecruitCount(i)
				.build();
			list.add(response);
		}

		return ResponseEntity.ok(CommonResponse.of(list));
	}

	@GetMapping("/api/v1/coffeechats/host")
	public ResponseEntity<CommonResponse> getHostCoffeeChatList() {
		List<FindCoffeeChatListResponse> list = new ArrayList<>();
		for (long i = 10; i <= 20; i++) {
			FindCoffeeChatListResponse response = FindCoffeeChatListResponse.builder()
				.coffeeChatId(i)
				.nickname("김영한")
				.job("backend")
				.title("주니어 백엔드 개발자를 대상으로 커피챗을 엽니다." + i)
				.status("모집중")
				.meetDate(LocalDateTime.now().plusMinutes(i))
				.createdDate(LocalDateTime.now())
				.currentRecruitCount(5L)
				.totalRecruitCount(i)
				.build();
			list.add(response);
		}

		return ResponseEntity.ok(CommonResponse.of(list));
	}
	@GetMapping("/api/v1/coffeechats")
	public ResponseEntity<CommonResponse> getCoffeeChatList() {
		List<FindCoffeeChatListResponse> list = new ArrayList<>();
		for (long i = 10; i <= 20; i++) {
			FindCoffeeChatListResponse response = FindCoffeeChatListResponse.builder()
				.coffeeChatId(i)
				.nickname("김영한")
				.job("backend")
				.title("주니어 백엔드 개발자를 대상으로 커피챗을 엽니다." + i)
				.status("모집중")
				.meetDate(LocalDateTime.now().plusMinutes(i))
				.createdDate(LocalDateTime.now())
				.currentRecruitCount(5L)
				.totalRecruitCount(i)
				.build();
			list.add(response);
		}

		return ResponseEntity.ok(CommonResponse.of(list));
	}

}
