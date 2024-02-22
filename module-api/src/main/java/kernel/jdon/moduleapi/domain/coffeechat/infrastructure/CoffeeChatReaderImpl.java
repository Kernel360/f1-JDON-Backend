package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.coffeechat.domain.CoffeeChat;
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
	public CoffeeChat findExistCoffeeChat(Long coffeeChatId) {
		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT::throwException);
	}
}
