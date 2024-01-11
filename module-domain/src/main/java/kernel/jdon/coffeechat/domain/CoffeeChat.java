package kernel.jdon.coffeechat.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

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
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SQLDelete(sql = "UPDATE coffee_chat SET is_deleted = true WHERE id = ?")
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
	private boolean isDeleted = false;

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

	public void updateCoffeeChat(CoffeeChat updateCoffeeChat) {
		validateRecruitCount(updateCoffeeChat.getTotalRecruitCount());
		validateMeetDate(updateCoffeeChat.getMeetDate());
		this.title = updateCoffeeChat.getTitle();
		this.content = updateCoffeeChat.getContent();
		this.totalRecruitCount = updateCoffeeChat.getTotalRecruitCount();
		this.meetDate = updateCoffeeChat.getMeetDate();
		this.openChatUrl = updateCoffeeChat.getOpenChatUrl();
		updateStatusByRecruitCount();
	}

	private void validateMeetDate(LocalDateTime newMeetDate) {
		if (this.meetDate.isBefore(LocalDateTime.now())) {
			// throw new ApiException(CoffeeChatErrorCode.EXPIRED_COFFEECHAT);
		}
		if (newMeetDate.isBefore(LocalDateTime.now())) {
			// throw new ApiException(CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW);
		}
	}

	private void validateRecruitCount(Long newTotalCount) {
		if (newTotalCount <= 0 || newTotalCount < this.currentRecruitCount) {
			// throw new ApiException(CoffeeChatErrorCode.INVALID_RECRUIT_COUNT);
		}
	}

	private void updateStatusByRecruitCount() {
		if (this.totalRecruitCount.equals(this.currentRecruitCount)) {
			this.coffeeChatStatus = CoffeeChatActiveStatus.FULL;
			return;
		}
		if (this.totalRecruitCount > this.currentRecruitCount) {
			this.coffeeChatStatus = CoffeeChatActiveStatus.OPEN;
		}
	}
}
