package kernel.jdon.moduleapi.global.page;

import org.springframework.data.domain.Slice;

import lombok.Getter;

@Getter
public class CustomJpaSliceInfo extends CustomSliceInfo {
	public CustomJpaSliceInfo(Slice<?> page) {
		super(page.getPageable().getPageNumber(),
			page.getPageable().getPageSize(),
			page.isLast(),
			page.isEmpty());
	}
}
