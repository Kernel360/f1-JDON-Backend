package kernel.jdon.moduleapi.domain.coffeechat.application;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.coffeechat.infrastructure.CoffeeChatRepository;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;

@SpringBootTest
class CoffeeChatApplyFacadeTest {
    @Autowired
    CoffeeChatRepository coffeeChatRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CoffeeChatFacade coffeeChatFacade;

    @DisplayName("동시에 여러 사용자가 커피챗을 신청할때, 총 모집인원을 초과하지 않는다.")
    @Test
    void givenConcurrentRequests_whenApplyCoffeeChat_thenShouldNotExceedTotalRecruitCount() throws
        InterruptedException {

        Member guestMember = createAndSaveGuestMember();
        Member hostMember = createAndSaveHostMember();
        CoffeeChat savedCoffeeChat = createAndSaveCoffeeChat(hostMember);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    coffeeChatFacade.applyCoffeeChat(savedCoffeeChat.getId(), guestMember.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        CoffeeChat actual = coffeeChatRepository.findById(savedCoffeeChat.getId()).orElseThrow();
        assertThat(actual.getCurrentRecruitCount()).isEqualTo(5L);
    }

    private CoffeeChat createAndSaveCoffeeChat(Member member) {
        CoffeeChat coffeeChat = CoffeeChat.builder()
            .member(member)
            .title("커피챗 제목")
            .content("커피챗 내용")
            .meetDate(LocalDateTime.now().plusDays(2))
            .openChatUrl("http://open.com")
            .totalRecruitCount(5L)
            .build();
        CoffeeChat savedCoffeeChat = coffeeChatRepository.save(coffeeChat);
        return savedCoffeeChat;
    }

    private Member createAndSaveGuestMember() {
        Member guestMember = Member.builder()
            .id(1L)
            .nickname("참여자")
            .email("b@gmail.com")
            .birth("2024-01-01")
            .gender(Gender.MALE)
            .role(MemberRole.ROLE_USER)
            .joinDate(LocalDateTime.now().minusDays(1))
            .accountStatus(MemberAccountStatus.ACTIVE)
            .socialProvider(SocialProviderType.KAKAO)
            .build();
        memberRepository.save(guestMember);
        return guestMember;
    }

    private Member createAndSaveHostMember() {
        Member guestMember = Member.builder()
            .id(2L)
            .nickname("개설자")
            .email("a@gmail.com")
            .birth("2024-01-01")
            .gender(Gender.MALE)
            .role(MemberRole.ROLE_USER)
            .joinDate(LocalDateTime.now().minusDays(1))
            .accountStatus(MemberAccountStatus.ACTIVE)
            .socialProvider(SocialProviderType.KAKAO)
            .build();
        memberRepository.save(guestMember);
        return guestMember;
    }
}