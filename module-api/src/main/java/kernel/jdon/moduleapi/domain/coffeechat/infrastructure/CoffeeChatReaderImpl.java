package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatReader;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatReaderImpl implements CoffeeChatReader {

	private final CoffeeChatRepository coffeeChatRepository;

	@Override
	public Page<CoffeeChatInfo.FindCoffeeChatListResponse> findCoffeeChatList(final Pageable pageable,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		final Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> readerInfo = coffeeChatRepository.findCoffeeChatList(
			pageable, command);
		List<CoffeeChatInfo.FindCoffeeChatListResponse> list = readerInfo.stream()
			.map(coffeeChat -> CoffeeChatInfo.FindCoffeeChatListResponse.of(coffeeChat))
			.toList();
		return new PageImpl<>(list, readerInfo.getPageable(), readerInfo.getTotalElements());
	}

	@Override
	public CoffeeChat findExistCoffeeChat(Long coffeeChatId) {
		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT::throwException);
	}
}
