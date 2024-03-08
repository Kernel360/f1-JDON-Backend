package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;

public interface CoffeeChatReader {
    CoffeeChat findExistCoffeeChat(Long coffeeChatId);

    CoffeeChatInfo.FindCoffeeChatListResponse findGuestCoffeeChatList(Long memberId,
        PageInfoRequest pageInfoRequest);

    CoffeeChatInfo.FindCoffeeChatListResponse findHostCoffeeChatList(Long memberId, PageInfoRequest pageInfoRequest);

    CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(PageInfoRequest pageInfoRequest,
        CoffeeChatCommand.FindCoffeeChatListRequest command);

    boolean existsByCoffeeChatIdAndMemberId(Long coffeeChatId, Long memberId);

    CoffeeChatMember findCoffeeChatMember(Long coffeeChatId, Long memberId);
}
