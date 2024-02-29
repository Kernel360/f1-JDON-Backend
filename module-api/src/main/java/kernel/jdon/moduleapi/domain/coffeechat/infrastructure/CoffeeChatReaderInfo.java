package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChatActiveStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatReaderInfo {

	@Getter
	public static class FindCoffeeChatListResponse {
		private final Long coffeeChatId;
		private final String nickname;
		private final String hostJobCategoryName;
		private final String title;
		private final String activeStatus;
		private final LocalDateTime meetDate;
		private final LocalDateTime createdDate;
		private final Long totalRecruitCount;
		private final Long currentRecruitCount;
		private final Long viewCount;

		@QueryProjection
		public FindCoffeeChatListResponse(Long coffeeChatId, String nickname, String job, String title,
			CoffeeChatActiveStatus activeStatus, LocalDateTime meetDate, LocalDateTime createdDate,
			Long totalRecruitCount, Long currentRecruitCount, Long viewCount) {
			this.coffeeChatId = coffeeChatId;
			this.nickname = nickname;
			this.hostJobCategoryName = job;
			this.title = title;
			this.activeStatus = activeStatus.getActiveStatus();
			this.meetDate = meetDate;
			this.createdDate = createdDate;
			this.totalRecruitCount = totalRecruitCount;
			this.currentRecruitCount = currentRecruitCount;
			this.viewCount = viewCount;
		}
	}
}
