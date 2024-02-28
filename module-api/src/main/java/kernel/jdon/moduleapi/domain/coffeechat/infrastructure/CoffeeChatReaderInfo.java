package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChatActiveStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatReaderInfo {

	@Getter
	public static class FindCoffeeChatListResponse {
		private Long coffeeChatId;
		private String nickname;
		private String hostJobCategoryName;
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

		@QueryProjection
		public FindCoffeeChatListResponse(Long coffeeChatId, String nickname, String job, String title,
			CoffeeChatActiveStatus activeStatus,
			LocalDateTime meetDate, LocalDateTime createdDate, Long totalRecruitCount, Long currentRecruitCount) {
			this.coffeeChatId = coffeeChatId;
			this.nickname = nickname;
			this.hostJobCategoryName = job;
			this.title = title;
			this.activeStatus = activeStatus.getActiveStatus();
			this.meetDate = meetDate;
			this.createdDate = createdDate;
			this.totalRecruitCount = totalRecruitCount;
			this.currentRecruitCount = currentRecruitCount;
		}
	}
}
