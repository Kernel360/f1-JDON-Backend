package kernel.jdon.moduledomain.coffeechat.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.CascadeType;
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
import kernel.jdon.moduledomain.base.AbstractEntity;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduledomain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SQLDelete(sql = "UPDATE coffee_chat SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coffee_chat")
public class CoffeeChat extends AbstractEntity {

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
    private Boolean isDeleted = false;

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

    @OneToMany(mappedBy = "coffeeChat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeChatMember> coffeeChatMemberList = new ArrayList<>();

    @Builder
    public CoffeeChat(Long id, String title, String content, LocalDateTime meetDate, String openChatUrl,
        Long totalRecruitCount, Member member) {
        this.id = id;
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

    public void addCoffeeChatMember(Member member) {
        CoffeeChatMember coffeeChatMember = CoffeeChatMember.builder()
            .coffeeChat(this)
            .member(member)
            .build();
        this.coffeeChatMemberList.add(coffeeChatMember);
        increaseCurrentRecruitCount();
        updateStatusByRecruitCount();
    }

    private void increaseCurrentRecruitCount() {
        this.currentRecruitCount += 1;
    }

    public void removeCoffeeChatMember(CoffeeChatMember coffeeChatMember) {
        this.coffeeChatMemberList.remove(coffeeChatMember);
        decreaseCurrentRecruitCount();
        updateStatusByRecruitCount();
    }

    private void decreaseCurrentRecruitCount() {
        this.currentRecruitCount -= 1;
    }

    public void updateCoffeeChat(CoffeeChat request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.totalRecruitCount = request.getTotalRecruitCount();
        this.meetDate = request.getMeetDate();
        this.openChatUrl = request.getOpenChatUrl();
        updateStatusByRecruitCount();
    }

    public boolean isNotOpen() {
        return coffeeChatStatus != CoffeeChatActiveStatus.OPEN;
    }

    public boolean isExpired() {
        return this.meetDate.isBefore(LocalDateTime.now());
    }

    public boolean isCurrentCountGreaterThan(Long newTotalCount) {
        return this.currentRecruitCount > newTotalCount;
    }

    private void updateStatusByRecruitCount() {
        if (this.totalRecruitCount.equals(this.currentRecruitCount)) {
            changeStatusFull();
        } else if (this.totalRecruitCount > this.currentRecruitCount) {
            changeStatusOpen();
        }
    }

    private void changeStatusFull() {
        this.coffeeChatStatus = CoffeeChatActiveStatus.FULL;
    }

    private void changeStatusOpen() {
        this.coffeeChatStatus = CoffeeChatActiveStatus.OPEN;
    }

    public void changeStatusClose() {
        this.coffeeChatStatus = CoffeeChatActiveStatus.CLOSE;
    }
}
