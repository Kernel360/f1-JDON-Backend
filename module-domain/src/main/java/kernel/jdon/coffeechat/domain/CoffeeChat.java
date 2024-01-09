package kernel.jdon.coffeechat.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kernel.jdon.base.BaseEntity;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.error.code.api.CoffeeChatErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coffee_chat")
public class CoffeeChat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
	private String title;

	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(name = "view_count", columnDefinition = "BIGINT default 0", nullable = false)
	private Long viewCount = 0L;

	@Column(name = "is_deleted", columnDefinition = "BOOLEAN", nullable = false)
	private boolean isDeleted;

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status", columnDefinition = "VARCHAR(50)", nullable = false)
	private CoffeeChatActiveStatus coffeeChatStatus = CoffeeChatActiveStatus.OPEN;

	@Column(name = "meet_date", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime meetDate;

	@Column(name = "open_chat_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String openChatUrl;

	@Column(name = "total_recruit_count", columnDefinition = "BIGINT", nullable = false)
	private Long totalRecruitCount;

	@Column(name = "current_recruit_count", columnDefinition = "BIGINT default 0", nullable = false)
	private Long currentRecruitCount = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", columnDefinition = "BIGINT")
	private Member member;

	@OneToMany(mappedBy = "coffeeChat")
	private List<CoffeeChatMember> coffeeChatMemberList = new ArrayList<>();

	@Builder
	public CoffeeChat(String title, String content, LocalDateTime meetDate, String openChatUrl,
		Long totalRecruitCount, Member member) {
		this.title = title;
		this.content = content;
		this.totalRecruitCount = totalRecruitCount;
		this.meetDate = meetDate;
		this.openChatUrl = openChatUrl;
		this.member = member;
	}

	public void increaseViewCount() {
		this.viewCount += 1;
	}

	public void updateCoffeeChat(UpdateCoffeeChatRequest request) {
		validateRecruitCount(request.getTotalRecruitCount());
		validateMeetDate(request.getMeetDate());
		this.title = request.getTitle();
		this.content = request.getContent();
		this.totalRecruitCount = request.getTotalRecruitCount();
		this.meetDate = request.getMeetDate();
		this.openChatUrl = request.getOpenChatUrl();
		updateStatus();
	}

	private void validateMeetDate(LocalDateTime newMeetDate) {
		if (this.meetDate.isBefore(LocalDateTime.now())) {
			throw new ApiException(CoffeeChatErrorCode.EXPIRED_COFFEECHAT);
		}
		if (newMeetDate.isBefore(LocalDateTime.now())) {
			throw new ApiException(CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW);
		}
	}

	private void validateRecruitCount(Long newTotalCount) {
		if (newTotalCount <= 0 || newTotalCount < this.currentRecruitCount) {
			throw new ApiException(CoffeeChatErrorCode.INVALID_RECRUIT_COUNT);
		}
	}

	private void updateStatus() {
		if (this.totalRecruitCount.equals(this.currentRecruitCount)) {
			this.coffeeChatStatus = CoffeeChatActiveStatus.FULL;
			return;
		}
		if (this.totalRecruitCount > this.currentRecruitCount) {
			this.coffeeChatStatus = CoffeeChatActiveStatus.OPEN;
		}
	}
}
