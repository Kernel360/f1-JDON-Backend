package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatDto {
	@Getter
	@Builder
	public static class FindCoffeeChatResponse {
		private final Long coffeeChatId;
		private final Long hostId;
		private final boolean isParticipant;
		private final String nickname;
		private final String hostJobCategoryName;
		private final String title;
		private final String content;
		private final Long viewCount;
		private final String status;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private final LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private final LocalDateTime createdDate;
		private final String openChatUrl;
		private final Long totalRecruitCount;
		private final Long currentRecruitCount;

		public boolean getIsParticipant() {
			return isParticipant;
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateCoffeeChatRequest {
		private String title;
		private String content;
		private Long totalRecruitCount;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		private String openChatUrl;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateCoffeeChatRequest {
		private String title;
		private String content;
		private Long totalRecruitCount;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		private String openChatUrl;
	}

	@Getter
	@Builder
	public static class FindCoffeeChatListResponse {
		private final List<FindCoffeeChat> content;
		private final CustomPageInfo pageInfo;
	}

	@Getter
	@Builder
	public static class FindCoffeeChat {
		private final Long coffeeChatId;
		private final String nickname;
		private final String hostJobCategoryName;
		private final String title;
		private final String activeStatus;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final Boolean isDeleted;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private final LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private final LocalDateTime createdDate;
		private final Long totalRecruitCount;
		private final Long currentRecruitCount;
	}
}
