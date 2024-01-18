package kernel.jdon.global.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class CustomPageInfo<T> implements Serializable {

	private List<T> content;
	private PageInfo pageInfo;

	public CustomPageInfo(List<T> content, Pageable pageable, long totalCount) {
		final PageImpl<T> page = new PageImpl<>(content, pageable, totalCount);

		this.content = page.getContent();
		this.pageInfo = new PageInfo(pageable, page.getTotalPages(), page.isFirst(), page.isLast(), page.isEmpty());
	}

	@Getter
	public class PageInfo implements Serializable {

		private Integer pageNumber;
		private Integer pageSize;
		private Integer totalPages;
		private boolean first;
		private boolean last;
		private boolean empty;

		public PageInfo(Pageable pageable, int totalPages, boolean first, boolean last, boolean empty) {
			this.pageNumber = pageable.getPageNumber();
			this.pageSize = pageable.getPageSize();
			this.totalPages = totalPages;
			this.first = first;
			this.last = last;
			this.empty = empty;
		}
	}
}