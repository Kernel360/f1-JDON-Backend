package kernel.jdon.moduleapi.domain.coffeechatmember.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.moduleapi.domain.coffeechat.entity.CoffeeChat;
import kernel.jdon.moduleapi.domain.member.entity.Member;

@Entity
@Table(name = "coffee_chat_member")
public class CoffeeChatMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(name = "created_date", columnDefinition = "DATETIME", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", columnDefinition = "BIGINT(50)")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coffee_chat_id", columnDefinition = "BIGINT(50)")
	private CoffeeChat coffeeChat;
}
