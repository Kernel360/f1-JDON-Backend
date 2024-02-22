package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.coffeechat.application.CoffeeChatFacade;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CoffeeChatController {

	private final CoffeeChatFacade coffeeChatFacade;
	private final CoffeeChatDtoMapper coffeeChatDtoMapper;

	@GetMapping("/api/v1/coffeechats/{id}")
	public ResponseEntity<CommonResponse<CoffeeChatDto.FindResponse>> getCoffeeChat(
		@PathVariable(name = "id") Long coffeeChatId) {
		CoffeeChatInfo.FindResponse info = coffeeChatFacade.getCoffeeChat(coffeeChatId);
		CoffeeChatDto.FindResponse response = coffeeChatDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}
}
