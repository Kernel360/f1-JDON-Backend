package kernel.jdon.modulebatch.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
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
public class InflearnScheduler {
    private final JobLauncher jobLauncher;
    private final Job inflearnCourseScrapingJob;
    private final SlackSender slackSender;

    @Scheduled(cron = "3 0 0 ? * MON")
    public void runInflearnCrawlJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("DATETIME", LocalDateTime.now().toString())
            .toJobParameters();
        try {
            slackSender.sendSchedulerStart("inflearnCrawlJob");
            jobLauncher.run(inflearnCourseScrapingJob, jobParameters);
            slackSender.sendSchedulerEnd("inflearnCrawlJob");
        } catch (JobExecutionException je) {
            log.error("[인프런_강의_스크래핑_job] 실행 중 에러 발생");
            log.info("JobExecution", je);
            throw new BatchException(BatchServerErrorCode.INTERNAL_SERVER_ERROR_SCHEDULER);
        }
    }
}
