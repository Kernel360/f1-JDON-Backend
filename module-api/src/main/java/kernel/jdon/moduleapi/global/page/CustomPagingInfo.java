package kernel.jdon.moduleapi.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CustomPagingInfo {
	private long pageNumber;
	private long pageSize;
	private boolean last;
	private boolean empty;
}
