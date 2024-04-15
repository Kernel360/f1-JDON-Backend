package kernel.jdon.modulebatch.job.jd.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;

import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JobScope
@RequiredArgsConstructor
public class PartWantedJobScrapingJobListener implements JobExecutionListener {
    private static final String JOB_NAME = "partWantedJdScrapingJob";

    private final SlackSender slackSender;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.STARTED) {
            slackSender.sendJobStart(JOB_NAME);
        }
        log.info("[PartWantedJobScrapingJobListener #beforeJob] jobExecution is " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            slackSender.sendJobError(JOB_NAME);
            log.error("[PartWantedJobScrapingJobListener #afterJob] jobExecution is FAILED!!! RECOVER ASAP");
        }
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            slackSender.sendJobEnd(JOB_NAME);
        }
        log.info("[PartWantedJobScrapingJobListener #afterJob] jobExecution is " + jobExecution.getStatus());
    }
}
