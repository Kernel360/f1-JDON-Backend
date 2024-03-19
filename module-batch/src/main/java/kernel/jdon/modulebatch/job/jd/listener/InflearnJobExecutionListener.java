package kernel.jdon.modulebatch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InflearnJobExecutionListener implements JobExecutionListener {

    private static final String PAGE_KEY = "currentPage";

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Job 시작");
        jobExecution.getExecutionContext().putInt(PAGE_KEY, 1);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job COMPLETED.");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Job FAILED");
        } else {
            log.info("Job 종료 status :" + jobExecution.getStatus());
        }
    }
}
