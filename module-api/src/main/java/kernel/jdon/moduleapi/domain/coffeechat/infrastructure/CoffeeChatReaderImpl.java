package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfoMapper;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatReader;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatReaderImpl implements CoffeeChatReader {

    private final CoffeeChatRepository coffeeChatRepository;
    private final CoffeeChatMemberRepository coffeeChatMemberRepository;
    private final CoffeeChatInfoMapper coffeeChatInfoMapper;

    @Override
    public CoffeeChat findExistCoffeeChat(Long coffeeChatId) {
        return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
            .orElseThrow(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT::throwException);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse findGuestCoffeeChatList(Long memberId,
        PageInfoRequest pageInfoRequest) {
        Page<CoffeeChatInfo.FindCoffeeChat> guestCoffeeChatPage = coffeeChatMemberRepository.findAllByMemberId(memberId,
                PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize()))
            .map(CoffeeChatMember::getCoffeeChat)
            .map(coffeeChatInfoMapper::listOf);

        List<CoffeeChatInfo.FindCoffeeChat> content = guestCoffeeChatPage.getContent();
        CustomJpaPageInfo pageInfo = new CustomJpaPageInfo(guestCoffeeChatPage);

        return new CoffeeChatInfo.FindCoffeeChatListResponse(content, pageInfo);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse findHostCoffeeChatList(Long memberId,
        PageInfoRequest pageInfoRequest) {
        Page<CoffeeChatInfo.FindCoffeeChat> hostCoffeeChatPage = coffeeChatRepository.findAllByMemberIdAndIsDeletedFalse(
                memberId,
                PageRequest.of(
                    pageInfoRequest.getPage(), pageInfoRequest.getSize()))
            .map(coffeeChatInfoMapper::listOf);

        List<CoffeeChatInfo.FindCoffeeChat> content = hostCoffeeChatPage.getContent();
        CustomPageInfo pageInfo = new CustomJpaPageInfo(hostCoffeeChatPage);

        return new CoffeeChatInfo.FindCoffeeChatListResponse(content, pageInfo);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(final PageInfoRequest pageInfoRequest,
        final CoffeeChatCommand.FindCoffeeChatListRequest command) {
        Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

        final Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> readerInfo = coffeeChatRepository.findCoffeeChatList(
            pageable, command);

        final List<CoffeeChatInfo.FindCoffeeChat> list = readerInfo.stream()
            .map(CoffeeChatInfo.FindCoffeeChat::of)
            .toList();

        return new CoffeeChatInfo.FindCoffeeChatListResponse(list, new CustomJpaPageInfo(readerInfo));
    }

    @Override
    public boolean existsByCoffeeChatIdAndMemberId(Long coffeeChatId, Long memberId) {
        return coffeeChatMemberRepository.existsByCoffeeChatIdAndMemberId(coffeeChatId, memberId);
    }

    @Override
    public CoffeeChatMember findCoffeeChatMember(Long coffeeChatId, Long memberId) {
        return coffeeChatMemberRepository.findByCoffeeChatIdAndMemberId(coffeeChatId, memberId)
            .orElseThrow(CoffeeChatErrorCode.NOT_FOUND_APPLICATION::throwException);
    }
}
