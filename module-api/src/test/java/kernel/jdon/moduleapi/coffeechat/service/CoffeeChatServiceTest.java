package kernel.jdon.moduleapi.coffeechat.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.coffeechat.service.CoffeeChatApplyFacade;
import kernel.jdon.member.domain.Gender;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberAccountStatus;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;

@SpringBootTest
class CoffeeChatServiceTest {
	@Autowired
	CoffeeChatRepository coffeeChatRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	CoffeeChatApplyFacade coffeeChatApplyFacade;

	@DisplayName("동시에 여러 사용자가 커피챗을 신청할때, 총 모집인원을 초과하지 않는다.")
	@Test
	void givenConcurrentRequests_whenApplyCoffeeChat_thenShouldNotExceedTotalRecruitCount() throws
		InterruptedException {

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

		CoffeeChat coffeeChat = CoffeeChat.builder()
			.title("커피챗 제목")
			.content("커피챗 내용")
			.meetDate(LocalDateTime.now().plusDays(2))
			.openChatUrl("http://open.com")
			.totalRecruitCount(5L)
			.build();
		CoffeeChat savedCoffeeChat = coffeeChatRepository.save(coffeeChat);
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(100);

		for (int i = 0; i < 100; i++) {
			executorService.submit(() -> {
				try {
					coffeeChatApplyFacade.apply(savedCoffeeChat.getId(), guestMember.getId());
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		CoffeeChat actual = coffeeChatRepository.findById(savedCoffeeChat.getId()).orElseThrow();
		assertThat(actual.getCurrentRecruitCount()).isEqualTo(5L);
	}

}