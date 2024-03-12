package kernel.jdon.moduleapi.global.page;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class CustomJpaPageInfo extends CustomPageInfo {
    public CustomJpaPageInfo(Page<?> page) {
        super(page.getPageable().getPageNumber(),
            page.getPageable().getPageSize(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.isEmpty());
    }
}
