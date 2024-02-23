package kernel.jdon.moduleapi.global.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomPageInfo {
	private long pageNumber;
	private long pageSize;
	private long totalPages;
	private boolean first;
	private boolean last;
	private boolean empty;
}
