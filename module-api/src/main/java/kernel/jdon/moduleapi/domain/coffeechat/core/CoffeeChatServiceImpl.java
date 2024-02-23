package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatServiceImpl implements CoffeeChatService {
	private final CoffeeChatReader coffeeChatReader;
	private final CoffeeChatInfoMapper coffeeChatInfoMapper;

	@Override
	public CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(
		final PageInfoRequest pageInfoRequest,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		final CoffeeChatInfo.FindCoffeeChatListResponse coffeeChatList = coffeeChatReader.findCoffeeChatList(
			pageInfoRequest, command);

		return coffeeChatList;
	}

	@Override
	public CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);

		return coffeeChatInfoMapper.of(findCoffeeChat);
	}

	@Override
	@Transactional
	public void increaseViewCount(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
		findCoffeeChat.increaseViewCount();
	}
}


