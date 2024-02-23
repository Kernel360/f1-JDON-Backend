package kernel.jdon.moduleapi.domain.coffeechat.application;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeChatFacade {

	private final CoffeeChatService coffeeChatService;

	public CustomPageResponse<CoffeeChatInfo.FindCoffeeChatListResponse> getCoffeeChatList(final Pageable pageable,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		return coffeeChatService.getCoffeeChatList(pageable, command);
	}

	public CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId) {
		CoffeeChatInfo.FindResponse findResponse = coffeeChatService.getCoffeeChat(coffeeChatId);
		coffeeChatService.increaseViewCount(coffeeChatId);

		return findResponse;
	}
}
