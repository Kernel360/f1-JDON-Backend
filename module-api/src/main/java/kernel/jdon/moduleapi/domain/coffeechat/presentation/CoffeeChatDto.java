package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatDto {
	@Getter
	@Builder
	public static class FindResponse {
		private Long coffeeChatId;
		private Long hostId;
		private String nickname;
		private String job;
		private String title;
		private String content;
		private Long viewCount;
		private String status;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime createdDate;
		private String openChatUrl;
		private Long totalRecruitCount;
		private Long currentRecruitCount;
	}

	@Getter
	public static class FindCoffeeChatListResponse {
		private List<FindCoffeeChat> content;
		private PageInfo pageInfo;

		public FindCoffeeChatListResponse(List<CoffeeChatInfo.FindCoffeeChatListResponse> content,
			CustomPageResponse.PageInfo pageInfo) {
			this.content = content.stream()
				.map(FindCoffeeChat::new)
				.toList();
			this.pageInfo = new PageInfo(pageInfo);
		}
	}

	@Getter
	public static class FindCoffeeChat {
		private Long coffeeChatId;
		private String nickname;
		private String job;
		private String title;
		private String activeStatus;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private Boolean isDeleted;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime createdDate;
		private Long totalRecruitCount;
		private Long currentRecruitCount;

		public FindCoffeeChat(CoffeeChatInfo.FindCoffeeChatListResponse info) {
			this.coffeeChatId = info.getCoffeeChatId();
			this.nickname = info.getNickname();
			this.job = info.getJob();
			this.title = info.getTitle();
			this.activeStatus = info.getActiveStatus();
			this.isDeleted = info.getIsDeleted();
			this.meetDate = info.getMeetDate();
			this.createdDate = info.getCreatedDate();
			this.totalRecruitCount = info.getTotalRecruitCount();
			this.currentRecruitCount = info.getCurrentRecruitCount();
		}
	}

	@Getter
	public static class PageInfo {
		private Integer pageNumber;
		private Integer pageSize;
		private Integer totalPages;
		private boolean first;
		private boolean last;
		private boolean empty;

		public PageInfo(CustomPageResponse.PageInfo pageInfo) {
			this.pageNumber = pageInfo.getPageNumber();
			this.pageSize = pageInfo.getPageSize();
			this.totalPages = pageInfo.getTotalPages();
			this.first = pageInfo.isFirst();
			this.last = pageInfo.isLast();
			this.empty = pageInfo.isEmpty();
		}
	}
}
