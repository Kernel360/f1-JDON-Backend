package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatReader;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatReaderImpl implements CoffeeChatReader {

    private final CoffeeChatRepository coffeeChatRepository;
    private final CoffeeChatMemberRepository coffeeChatMemberRepository;

    @Override
    public CoffeeChat findExistCoffeeChat(Long coffeeChatId) {
        return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
            .orElseThrow(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT::throwException);
    }

    @Override
    public Page<CoffeeChatMember> findAllByMemberId(Long memberId, Pageable pageable) {
        return coffeeChatMemberRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    public Page<CoffeeChat> findHostCoffeeChatList(Long memberId, Pageable pageable) {
        return coffeeChatRepository.findAllByMemberIdAndIsDeletedFalse(memberId, pageable);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(final PageInfoRequest pageInfoRequest,
        final CoffeeChatCommand.FindCoffeeChatListRequest command) {
        Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

        final Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> readerInfo = coffeeChatRepository.findCoffeeChatList(
            pageable, command);

        final List<CoffeeChatInfo.FindCoffeeChat> list = readerInfo.stream()
            .map(coffeeChat -> CoffeeChatInfo.FindCoffeeChat.of(coffeeChat))
            .toList();

		return new CoffeeChatInfo.FindCoffeeChatListResponse(list, new CustomJpaPageInfo(readerInfo));
	}
}
