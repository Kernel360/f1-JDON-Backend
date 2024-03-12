package kernel.jdon.moduleapi.global.page;

import lombok.Getter;

@Getter
public class CustomSliceInfo extends CustomPagingInfo {

    public CustomSliceInfo(long pageNumber, long pageSize, boolean last, boolean empty) {
        super(pageNumber, pageSize, last, empty);
    }
}
