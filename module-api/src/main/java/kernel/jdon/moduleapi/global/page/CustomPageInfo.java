package kernel.jdon.moduleapi.global.page;

import lombok.Getter;

@Getter
public class CustomPageInfo extends CustomPagingInfo {
    private final boolean first;
    private final long totalPages;

    public CustomPageInfo(long pageNumber, long pageSize, long totalPages, boolean first, boolean last, boolean empty) {
        super(pageNumber, pageSize, last, empty);
        this.first = first;
        this.totalPages = totalPages;
    }
}
