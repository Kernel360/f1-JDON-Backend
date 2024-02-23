package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatReader;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatReaderImpl implements CoffeeChatReader {

	private final CoffeeChatRepository coffeeChatRepository;

	@Override
	public CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(final PageInfoRequest pageInfoRequest,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

		final Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> readerInfo = coffeeChatRepository.findCoffeeChatList(
			pageable, command);

		final List<CoffeeChatInfo.FindCoffeeChatInfo> list = readerInfo.stream()
			.map(coffeeChat -> CoffeeChatInfo.FindCoffeeChatInfo.of(coffeeChat))
			.toList();

		final CustomPageInfo customPageInfo = new CustomPageInfo(readerInfo.getPageable().getPageNumber(),
			readerInfo.getTotalElements(), readerInfo.getPageable().getPageSize(), readerInfo.isFirst(),
			readerInfo.isLast(), readerInfo.isEmpty());

		return new CoffeeChatInfo.FindCoffeeChatListResponse(list, customPageInfo);
	}

	@Override
	public CoffeeChat findExistCoffeeChat(Long coffeeChatId) {
		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT::throwException);
	}
}
