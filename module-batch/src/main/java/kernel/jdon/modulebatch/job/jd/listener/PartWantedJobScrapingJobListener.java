package kernel.jdon.modulebatch.job.jd.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PartWantedJobScrapingJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("[PartWantedJobScrapingJobListener #beforeJob] jobExecution is " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("[PartWantedJobScrapingJobListener #afterJob] jobExecution is FAILED!!! RECOVER ASAP");
        }
        log.info("[PartWantedJobScrapingJobListener #afterJob] jobExecution is " + jobExecution.getStatus());
    }
}
