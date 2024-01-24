package kernel.jdon.global.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class CustomPageResponse<T> {

	private List<T> content;
	private PageInfo pageInfo;

	public CustomPageResponse(Page<T> page) {
		this.content = page.getContent();
		this.pageInfo = new PageInfo(page);
	}

	@Getter
	public class PageInfo implements Serializable {

		private Integer pageNumber;
		private Integer pageSize;
		private Integer totalPages;
		private boolean first;
		private boolean last;
		private boolean empty;

		public PageInfo(Page page) {
			this.pageNumber = page.getPageable().getPageNumber();
			this.pageSize = page.getPageable().getPageSize();
			this.totalPages = page.getTotalPages();
			this.first = page.isFirst();
			this.last = page.isLast();
			this.empty = page.isEmpty();
		}
	}
}