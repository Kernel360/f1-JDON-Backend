package kernel.jdon.moduleapi.global.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomPageResponse<T> {

	private List<T> content;
	private PageInfo pageInfo;

	public static CustomPageResponse of(Page page) {
		return new CustomPageResponse(page.getContent(), new PageInfo(page));
	}

	@Getter
	public static class PageInfo implements Serializable {

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