package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatReader {
    CoffeeChat findExistCoffeeChat(Long coffeeChatId);

    Page<CoffeeChatMember> findCoffeeChatMemberListByMemberId(Long memberId, Pageable pageable);

    Page<CoffeeChat> findCoffeeChatListByMemberId(Long memberId, Pageable pageable);

    CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(PageInfoRequest pageInfoRequest,
        CoffeeChatCommand.FindCoffeeChatListRequest command);

    boolean existsByCoffeeChatIdAndMemberId(Long coffeeChatId, Long memberId);

}
