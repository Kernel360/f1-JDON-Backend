package kernel.jdon.coffeechat.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.coffeechat.dto.request.CoffeeChatCondition;
import kernel.jdon.coffeechat.dto.request.CoffeeChatSortCondition;
import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.ApplyCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.DeleteCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatListResponse;
import kernel.jdon.coffeechat.dto.response.UpdateCoffeeChatResponse;
import kernel.jdon.coffeechat.service.CoffeeChatApplyFacade;
import kernel.jdon.coffeechat.service.CoffeeChatService;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.global.annotation.LoginUser;
import kernel.jdon.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CoffeeChatController {

	private final CoffeeChatService coffeeChatService;
	private final CoffeeChatApplyFacade coffeeChatApplyFacade;

	@GetMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse> get(
		@PathVariable(name = "id") Long coffeeChatId,
		@LoginUser SessionUserInfo sessionUser) {

		return ResponseEntity.ok().body(CommonResponse.of(coffeeChatService.find(coffeeChatId, sessionUser.getId())));
	}

	@PostMapping("/api/v1/coffeechats")
	public ResponseEntity<CommonResponse> save(
		@RequestBody CreateCoffeeChatRequest request,
		@LoginUser SessionUserInfo sessionUser) {

		log.info("request: {}", request.toString());
		log.info("sessionUser: {}", sessionUser.getId());
		CreateCoffeeChatResponse response = coffeeChatService.create(request, sessionUser.getId());
		URI uri = URI.create("/v1/coffeechats/" + response.getCoffeeChatId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/coffeechats/guest")
	public ResponseEntity<CommonResponse> getGuestCoffeeChatList(
		@LoginUser SessionUserInfo sessionUser,
		@PageableDefault(size = 12) Pageable pageable) {

		CustomPageResponse<FindCoffeeChatListResponse> response = coffeeChatService.findGuestCoffeeChatList(
			sessionUser.getId(), pageable);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/coffeechats/host")
	public ResponseEntity<CommonResponse> getHostCoffeeChatList(
		@LoginUser SessionUserInfo sessionUser,
		@PageableDefault(size = 12) Pageable pageable) {

		CustomPageResponse<FindCoffeeChatListResponse> response = coffeeChatService.findHostCoffeeChatList(
			sessionUser.getId(), pageable);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/coffeechats")
	public ResponseEntity<CommonResponse> getCoffeeChatList(
		@PageableDefault(size = 12) Pageable pageable,
		@RequestParam(value = "sort", defaultValue = "") CoffeeChatSortCondition sort,
		@RequestParam(value = "keyword", defaultValue = "") String keyword,
		@RequestParam(value = "jobCategory", defaultValue = "") Long jobCategory) {

		CustomPageResponse<FindCoffeeChatListResponse> response =
			coffeeChatService.findCoffeeChatList(pageable, new CoffeeChatCondition(sort, keyword, jobCategory));

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@PutMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse> modify(
		@PathVariable(name = "id") Long coffeeChatId,
		@RequestBody UpdateCoffeeChatRequest request) {

		UpdateCoffeeChatResponse response = coffeeChatService.update(coffeeChatId, request);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@DeleteMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse> remove(@PathVariable(name = "id") Long coffeeChatId) {
		DeleteCoffeeChatResponse response = coffeeChatService.delete(coffeeChatId);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@PostMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse> apply(
		@PathVariable(name = "id") Long coffeeChatId,
		@LoginUser SessionUserInfo sessionUser) {

		ApplyCoffeeChatResponse response = coffeeChatApplyFacade.apply(coffeeChatId, sessionUser.getId());

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
