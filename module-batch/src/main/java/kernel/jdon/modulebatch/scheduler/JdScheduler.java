package kernel.jdon.modulebatch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.global.exception.BatchException;
import kernel.jdon.modulebatch.global.exception.BatchServerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdScheduler {
    private final JobLauncher jobLauncher;
    private final Job 부분_원티드_채용공고_스크래핑_job;
    private final Job 전체_원티드_채용공고_스크래핑_job;

    @Scheduled(cron = "0 0 0 ? * MON,THU") // 매주 월요일 목요일 00시
    public void runPartWantedJdScrapingJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        try {
            log.info("[부분_원티드_채용공고_스크래핑_job] 스케줄러 시작");
            jobLauncher.run(부분_원티드_채용공고_스크래핑_job, jobParameters);
            log.info("[부분_원티드_채용공고_스크래핑_job] 스케줄러 종료");
        } catch (Exception e) {
            log.error("[부분_원티드_채용공고_스크래핑_job] 실행중 Error 발생");
            throw new BatchException(BatchServerErrorCode.INTERNAL_SERVER_ERROR_SCHEDULER);
        }
    }

    @Scheduled(cron = "0 0 0 ? * WED") // 매주 수요일 00시
    public void runAllWantedJdScrapingJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        try {
            log.info("[전체_원티드_채용공고_스크래핑_job] 스케줄러 시작");
            jobLauncher.run(전체_원티드_채용공고_스크래핑_job, jobParameters);
            log.info("[전체_원티드_채용공고_스크래핑_job] 스케줄러 종료");
        } catch (Exception e) {
            log.error("[전체_원티드_채용공고_스크래핑_job] 실행중 Error 발생");
            throw new BatchException(BatchServerErrorCode.INTERNAL_SERVER_ERROR_SCHEDULER);
        }
    }

}
