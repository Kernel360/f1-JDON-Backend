package kernel.jdon.moduleapi.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CustomPagingInfo {
    private final long pageNumber;
    private final long pageSize;
    private final boolean last;
    private final boolean empty;
}
