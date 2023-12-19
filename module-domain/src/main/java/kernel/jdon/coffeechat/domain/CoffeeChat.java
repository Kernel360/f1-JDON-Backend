package kernel.jdon.coffeechat.domain;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import kernel.jdon.base.BaseEntity;
import kernel.jdon.member.domain.Member;

@Entity
@Table(name = "coffee_chat")
public class CoffeeChat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
	private String title;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@Column(name = "view_count", columnDefinition = "BIGINT(50)", nullable = false)
	private Long viewCount;

	@Column(name = "is_deleted", columnDefinition = "BOOLEAN", nullable = false)
	private boolean isDeleted;

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status", columnDefinition = "VARCHAR(50)", nullable = false)
	private CoffeeChatActiveStatus coffeeChatStatus;

	@Column(name = "meet_date", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime meetDate;

	@Column(name = "open_chat_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String openChatUrl;

	@Column(name = "total_recruit_count", columnDefinition = "BIGINT(50)")
	private Long totalRecruitCount;

	@Column(name = "current_recruit_count", columnDefinition = "BIGINT(50)")
	private Long currentRecruitCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", columnDefinition = "BIGINT(50)")
	private Member member;

}
