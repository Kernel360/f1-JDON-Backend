package kernel.jdon.modulebatch.job.jd.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;

import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@RequiredArgsConstructor
public class AllBackendWantedJobScrapingJStepListener implements StepExecutionListener {
    private static final String STEP_NAME = "allBackendWantedJdScrapingStep";

    private final SlackSender slackSender;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.STARTED) {
            slackSender.sendStepStart(STEP_NAME);
        }
        log.info("[AllBackendWantedJobScrapingJStepListener #beforeJob] stepExecution is " + stepExecution.getStatus());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.FAILED) {
            slackSender.sendStepError(STEP_NAME);
            log.error("[AllBackendWantedJobScrapingJStepListener #afterJob] stepExecution is FAILED!!! RECOVER ASAP");
        }
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            slackSender.sendStepEnd(STEP_NAME);

        }
        log.info("[AllBackendWantedJobScrapingJStepListener #afterJob] stepExecution is " + stepExecution.getStatus());

        return new ExitStatus(stepExecution.getStatus().name());
    }
}
