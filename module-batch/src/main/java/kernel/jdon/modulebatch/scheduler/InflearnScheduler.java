package kernel.jdon.modulebatch.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InflearnScheduler {
    private final JobLauncher jobLauncher;
    private final Job inflearnCrawlJob;

    @Autowired
    public InflearnScheduler(JobLauncher jobLauncher, @Qualifier("inflearnCrawlJob") Job inflearnCrawlJob) {
        this.jobLauncher = jobLauncher;
        this.inflearnCrawlJob = inflearnCrawlJob;
    }

    @Scheduled(fixedDelay = 1000)
    public void executeInflearnCrawlJob() {
        executeJob(inflearnCrawlJob);
    }

    private void executeJob(Job job) {
        try {
            jobLauncher.run(
                job,
                new JobParametersBuilder()
                    .addString("DATETIME", LocalDateTime.now().toString())
                    .toJobParameters());
        } catch (JobExecutionException je) {
            log.info("JobExecution : " + je);
        }
    }
}
