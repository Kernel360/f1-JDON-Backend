package kernel.jdon.member.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

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
import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.memberskill.domain.MemberSkill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

	@OneToMany(mappedBy = "member")
	private final List<CoffeeChat> hostChatList = new ArrayList<>();
	@OneToMany(mappedBy = "member")
	private final List<CoffeeChatMember> guestChatList = new ArrayList<>();
	@OneToMany(mappedBy = "member")
	private final List<MemberSkill> memberSkillList = new ArrayList<>();
	@OneToMany(mappedBy = "member")
	private final List<Favorite> favoriteList = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
	private String email;
	@Column(name = "nickname", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
	private String nickname;
	@Column(name = "birth", columnDefinition = "VARCHAR(11)", nullable = false)
	private String birth;
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", columnDefinition = "VARCHAR(11)", nullable = false)
	private Gender gender;
	@CreatedDate
	@Column(name = "join_date", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime joinDate;
	@Column(name = "last_login_date", columnDefinition = "DATETIME")
	private LocalDateTime lastLoginDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", columnDefinition = "VARCHAR(10)", nullable = false)
	private MemberRole role;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_status", columnDefinition = "VARCHAR(10)", nullable = false)
	private MemberAccountStatus accountStatus;
	@Column(name = "withdraw_date", columnDefinition = "DATETIME")
	private LocalDateTime withdrawDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "social_provider", columnDefinition = "VARCHAR(20)", nullable = false)
	private SocialProviderType socialProvider;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
	private JobCategory jobCategory;

	@Builder
	public Member(Long id, String email, String nickname, String birth, Gender gender, LocalDateTime joinDate,
		LocalDateTime lastLoginDate, MemberRole role, MemberAccountStatus accountStatus, LocalDateTime withdrawDate,
		SocialProviderType socialProvider, JobCategory jobCategory) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.birth = birth;
		this.gender = gender;
		this.joinDate = joinDate;
		this.lastLoginDate = lastLoginDate;
		this.role = role;
		this.accountStatus = accountStatus;
		this.withdrawDate = withdrawDate;
		this.socialProvider = socialProvider;
		this.jobCategory = jobCategory;
	}

	public boolean isActiveMember() {
		return this.accountStatus != MemberAccountStatus.WITHDRAW;
	}

	public boolean isRightSocialProvider(SocialProviderType socialProvider) {
		return this.socialProvider == socialProvider;
	}
}
