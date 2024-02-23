package kernel.jdon.moduleapi.global.page;

import lombok.Getter;

@Getter
public class PageInfoRequest {
	private int page;
	private int size;

	public PageInfoRequest(int page, int size) {
		this.page = page;
		this.size = size;
	}
}
