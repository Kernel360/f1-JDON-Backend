package kernel.jdon.modulecrawler.domain.jd.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import kernel.jdon.modulecrawler.domain.jd.core.JdService;
import kernel.jdon.modulecrawler.global.exception.CrawlerException;

@DisplayName("채용공고_스크래핑_따닥_테스트")
@SpringBootTest
class JdFacadeTest {
    @MockBean
    private JdService jdService;

    @Autowired
    private JdFacade jdFacade;

    @DisplayName("멀티스레드 환경에서 다수의 요청이 들어와도 1개의 요청만 scrapeWantedJd 메서드의 락을 획득한다.")
    @Test
    void 채용공고_스크래핑_동시성_테스트() throws InterruptedException {
        // given
        final int threadCount = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final AtomicInteger successCount = new AtomicInteger();
        final AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    jdFacade.scrapeWantedJd();
                    successCount.getAndIncrement();
                } catch (CrawlerException e) {
                    failCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        assertAll(
            () -> assertThat(successCount.get()).isEqualTo(1),
            () -> assertThat(failCount.get()).isEqualTo(9)
        );
    }
}
