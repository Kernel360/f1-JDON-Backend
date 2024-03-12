package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;

public interface CustomCoffeeChatRepository {
    Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> findCoffeeChatList(Pageable pageable,
        CoffeeChatCommand.FindCoffeeChatListRequest readerCommand);
}
