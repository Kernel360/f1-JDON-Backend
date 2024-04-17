package kernel.jdon.modulecrawler.domain.jd.application;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import kernel.jdon.modulecrawler.domain.jd.core.JdService;
import kernel.jdon.modulecrawler.domain.jd.error.JdErrorCode;
import kernel.jdon.modulecrawler.global.exception.CrawlerException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdFacade {
    private static final String LOCK_KEY = "scrape:jd";

    private final JdService jdService;
    private final RedissonClient redissonClient;
    private boolean isLocked = false;

    public void scrapeWantedJd() {
        final RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            final boolean isAvailable = lock.tryLock(0, TimeUnit.SECONDS);
            if (!isAvailable) {
                throw new CrawlerException(JdErrorCode.CONFLICT_FAIL_LOCK);
            }
            jdService.scrapeWantedJd();
        } catch (InterruptedException e) {
            throw new CrawlerException(JdErrorCode.THREAD_INTERRUPTED);
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }
}
