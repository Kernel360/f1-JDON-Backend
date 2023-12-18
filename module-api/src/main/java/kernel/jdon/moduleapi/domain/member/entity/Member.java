package kernel.jdon.moduleapi.domain.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
import kernel.jdon.moduleapi.domain.coffeechat.entity.CoffeeChat;
import kernel.jdon.moduleapi.domain.coffeechatmember.entity.CoffeeChatMember;
import kernel.jdon.moduleapi.domain.favorite.entity.Favorite;
import kernel.jdon.moduleapi.domain.jobcategory.entity.JobCategory;
import kernel.jdon.moduleapi.domain.memberskill.entity.MemberSkill;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false)
	private String email;

	@Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
	private String password;

	@Column(name = "nickname", columnDefinition = "VARCHAR(255)", nullable = false)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT(50)")
	private JobCategory jobCategory;

	@OneToMany(mappedBy = "member")
	private ArrayList<CoffeeChat> hostChatList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private ArrayList<CoffeeChatMember> guestChatList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private ArrayList<MemberSkill> memberSkillList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private ArrayList<Favorite> favoriteList = new ArrayList<>();

}