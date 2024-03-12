package kernel.jdon.moduleapi.global.page;

import java.util.Optional;

import lombok.Getter;

@Getter
public class PageInfoRequest {
    private final int page;
    private final int size;

    public PageInfoRequest(final String page, final String size) {
        final Integer getPage = getIntegerValue(page);
        final Integer getSize = getIntegerValue(size);
        this.page = initPage(getPage);
        this.size = initSize(getSize);
    }

    private Integer getIntegerValue(final String value) {
        return isInteger(value) ? Integer.parseInt(value) : null;
    }

    private int initPage(final Integer page) {
        final int defaultPage = 0;
        final int minimumPage = 0;
        return Optional.ofNullable(page)
            .map(p -> p < minimumPage ? defaultPage : page)
            .orElse(defaultPage);
    }

    private int initSize(final Integer size) {
        final int defaultSize = 12;
        final int minimumSize = 1;
        return Optional.ofNullable(size)
            .map(p -> p < minimumSize ? defaultSize : size)
            .orElse(defaultSize);
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
