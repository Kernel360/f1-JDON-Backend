package kernel.jdon.coffeechat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindCoffeeChatListResponse {

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

	@Builder
	public FindCoffeeChatListResponse(Long coffeeChatId, String nickname, String job, String title, String activeStatus,
		boolean isDeleted, LocalDateTime meetDate, LocalDateTime createdDate, Long totalRecruitCount, Long currentRecruitCount) {
		this.coffeeChatId = coffeeChatId;
		this.nickname = nickname;
		this.job = job;
		this.title = title;
		this.activeStatus = activeStatus;
		this.isDeleted = isDeleted;
		this.meetDate = meetDate;
		this.createdDate = createdDate;
		this.totalRecruitCount = totalRecruitCount;
		this.currentRecruitCount = currentRecruitCount;
	}


	@QueryProjection
	public FindCoffeeChatListResponse(Long coffeeChatId, String nickname, String job, String title, CoffeeChatActiveStatus activeStatus,
		LocalDateTime meetDate, LocalDateTime createdDate, Long totalRecruitCount, Long currentRecruitCount) {
		this.coffeeChatId = coffeeChatId;
		this.nickname = nickname;
		this.job = job;
		this.title = title;
		this.activeStatus = activeStatus.getActiveStatus();
		this.meetDate = meetDate;
		this.createdDate = createdDate;
		this.totalRecruitCount = totalRecruitCount;
		this.currentRecruitCount = currentRecruitCount;
	}

	public static FindCoffeeChatListResponse of(CoffeeChat coffeeChat) {
		return FindCoffeeChatListResponse.builder()
			.coffeeChatId(coffeeChat.getId())
			.nickname(coffeeChat.getMember().getNickname())
			.job((coffeeChat.getMember().getJobCategory().getName()))
			.title(coffeeChat.getTitle())
			.activeStatus(coffeeChat.getCoffeeChatStatus().getActiveStatus())
			.isDeleted(coffeeChat.isDeleted())
			.meetDate(coffeeChat.getMeetDate())
			.createdDate(coffeeChat.getCreatedDate())
			.totalRecruitCount(coffeeChat.getTotalRecruitCount())
			.currentRecruitCount(coffeeChat.getCurrentRecruitCount())
			.build();
	}

}
