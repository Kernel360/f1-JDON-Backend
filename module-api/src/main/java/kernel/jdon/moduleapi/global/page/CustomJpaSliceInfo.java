package kernel.jdon.moduleapi.global.page;

import org.springframework.data.domain.Slice;

import lombok.Getter;

@Getter
public class CustomJpaSliceInfo extends CustomSliceInfo {
	public CustomJpaSliceInfo(Slice<?> slice) {
		super(slice.getPageable().getPageNumber(),
			slice.getPageable().getPageSize(),
			slice.isLast(),
			slice.isEmpty());
	}
}
