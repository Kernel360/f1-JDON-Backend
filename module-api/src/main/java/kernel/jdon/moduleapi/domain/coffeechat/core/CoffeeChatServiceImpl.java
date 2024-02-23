package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
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
	public CustomPageResponse<CoffeeChatInfo.FindCoffeeChatListResponse> getCoffeeChatList(final Pageable pageable,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		final Page<CoffeeChatInfo.FindCoffeeChatListResponse> coffeeChatList = coffeeChatReader.findCoffeeChatList(
			pageable, command);

		return CustomPageResponse.of(coffeeChatList);
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


