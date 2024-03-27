package kernel.jdon.modulebatch.job.jd;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.domain.wantedjd.repository.WantedJdRepository;
import kernel.jdon.modulebatch.job.jd.listener.AllBackendWantedJobScrapingJStepListener;
import kernel.jdon.modulebatch.job.jd.listener.AllWantedJobScrapingJobListener;
import kernel.jdon.modulebatch.job.jd.listener.UpdateJdStatusStepListener;
import kernel.jdon.modulebatch.job.jd.reader.AllBackendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.AllFrontendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.writer.WantedJdItemWriter;
import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AllWantedJobScrapingJobConfig {
    private static final String JOB_NAME = "allWantedJdScrapingJob";
    private static final int CHUNK_SIZE = 1;

    private final AllFrontendWantedJdItemReader allFrontendWantedJdItemReader;
    private final AllBackendWantedJdItemReader allBackendWantedJdItemReader;
    private final WantedJdItemWriter wantedJdItemWriter;
    private final PlatformTransactionManager platformTransactionManager;
    private final WantedJdRepository wantedJdRepository;
    private final SlackSender slackSender;

    /**
     * [전체_원티드_채용공고_스크래핑]
     * 직군별 전체 원티드 JD 데이터를 수집 및 적재한다.
     * 기존에 존재하는 JD는 update, 신규 JD는 insert
     */
    @Bean
    public Job allWantedJdScrapingJob(JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(new AllWantedJobScrapingJobListener())
            .start(allBackendWantedJdScrapingStep(jobRepository)) // 백엔드 JD 스크래핑
            .next(allFrontendWantedJdScrapingStep(jobRepository)) // 프론트엔드 JD 스크래핑
            .next(updateJdStatusStep(jobRepository)) // JD 상태 업데이트
            .build();
    }

    @Bean
    @JobScope
    public Step allBackendWantedJdScrapingStep(JobRepository jobRepository) {
        return new StepBuilder("allBackendWantedJdScrapingStep", jobRepository)
            .listener(new AllBackendWantedJobScrapingJStepListener(slackSender))
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(allBackendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    @JobScope
    public Step allFrontendWantedJdScrapingStep(JobRepository jobRepository) {
        return new StepBuilder("allFrontendWantedJdScrapingStep", jobRepository)
            .listener(new AllBackendWantedJobScrapingJStepListener(slackSender))
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(allFrontendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    @JobScope
    public Step updateJdStatusStep(JobRepository jobRepository) {
        return new StepBuilder("updateJdStatusStep", jobRepository)
            .listener(new UpdateJdStatusStepListener(slackSender))
            .tasklet((contribution, chunkContext) -> {
                wantedJdRepository.updateWantedJdActiveStatus();
                return RepeatStatus.FINISHED;
            }, platformTransactionManager)
            .build();
    }
}
