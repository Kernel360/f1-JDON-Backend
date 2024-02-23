package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.coffeechat.application.CoffeeChatFacade;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortCondition;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CoffeeChatController {

	private final CoffeeChatFacade coffeeChatFacade;
	private final CoffeeChatDtoMapper coffeeChatDtoMapper;

	@GetMapping("/api/v1/coffeechats")
	public ResponseEntity<CommonResponse<CoffeeChatInfo.FindCoffeeChatListResponse>> getCoffeeChatList(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "12") int size,
		@RequestParam(value = "sort", defaultValue = "") CoffeeChatSortCondition sort,
		@RequestParam(value = "keyword", defaultValue = "") String keyword,
		@RequestParam(value = "jobCategory", defaultValue = "") Long jobCategory) {

		CoffeeChatCommand.FindCoffeeChatListRequest request = coffeeChatDtoMapper.of(
			new CoffeeChatCondition(sort, keyword, jobCategory));
		CoffeeChatInfo.FindCoffeeChatListResponse info = coffeeChatFacade.getCoffeeChatList(
			new PageInfoRequest(page, size), request);
		CoffeeChatDto.FindCoffeeChatListResponse response = coffeeChatDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse<CoffeeChatDto.FindResponse>> getCoffeeChat(
		@PathVariable(name = "id") Long coffeeChatId) {
		CoffeeChatInfo.FindResponse info = coffeeChatFacade.getCoffeeChat(coffeeChatId);
		CoffeeChatDto.FindResponse response = coffeeChatDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}
}
