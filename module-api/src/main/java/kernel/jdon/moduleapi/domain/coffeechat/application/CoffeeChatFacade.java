package kernel.jdon.moduleapi.domain.coffeechat.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeChatFacade {

	private final CoffeeChatService coffeeChatService;

	public CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId) {
		CoffeeChatInfo.FindResponse findResponse = coffeeChatService.getCoffeeChat(coffeeChatId);
		coffeeChatService.increaseViewCount(coffeeChatId);

		return findResponse;
	}

}
