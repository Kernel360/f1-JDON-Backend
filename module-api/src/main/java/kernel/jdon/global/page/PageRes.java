package kernel.jdon.global.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class PageRes<T> implements Serializable {

	private List<T> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private boolean first;
	private boolean last;
	private boolean empty;

	public PageRes(List<T> content, Pageable pageable, long totalCount) {

		final PageImpl<T> page = new PageImpl<>(content, pageable, totalCount);

		this.content = page.getContent();
		this.pageNumber = pageable.getPageNumber();
		this.pageSize = pageable.getPageSize();
		this.totalPages = page.getTotalPages();
		this.first = page.isFirst();
		this.last = page.isLast();
		this.empty = page.isEmpty();
	}
}
