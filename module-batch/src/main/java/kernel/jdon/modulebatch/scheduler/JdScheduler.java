package kernel.jdon.modulebatch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.global.exception.BatchException;
import kernel.jdon.modulebatch.global.exception.BatchServerErrorCode;
import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdScheduler {
    private final JobLauncher jobLauncher;
    private final Job partWantedJdScrapingJob;
    private final Job allWantedJdScrapingJob;
    private final SlackSender slackSender;

    @Scheduled(cron = "0 0 1 ? * SUN-TUE,THU-SAT") // 수요일을 제외한 모든 요일 01시
    public void runPartWantedJdScrapingJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        try {
            slackSender.sendSchedulerStart("partWantedJdScrapingJob");
            jobLauncher.run(partWantedJdScrapingJob, jobParameters);
            slackSender.sendSchedulerEnd("partWantedJdScrapingJob");
        } catch (Exception e) {
            log.error("[partWantedJdScrapingJob] 실행중 Error 발생");
            throw new BatchException(BatchServerErrorCode.INTERNAL_SERVER_ERROR_SCHEDULER);
        }
    }

    @Scheduled(cron = "0 0 1 ? * WED") // 매주 수요일 01시
    public void runAllWantedJdScrapingJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        try {
            slackSender.sendSchedulerStart("allWantedJdScrapingJob");
            jobLauncher.run(allWantedJdScrapingJob, jobParameters);
            slackSender.sendSchedulerEnd("allWantedJdScrapingJob");
        } catch (Exception e) {
            log.error("[allWantedJdScrapingJob] 실행중 Error 발생");
            throw new BatchException(BatchServerErrorCode.INTERNAL_SERVER_ERROR_SCHEDULER);
        }
    }

}
