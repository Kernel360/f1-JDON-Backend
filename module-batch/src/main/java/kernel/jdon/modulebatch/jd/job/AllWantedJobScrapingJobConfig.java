package kernel.jdon.modulebatch.jd.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.jd.processor.WantedJdItemProcessor;
import kernel.jdon.modulebatch.jd.reader.BackendWantedJdItemReader;
import kernel.jdon.modulebatch.jd.reader.FrontendWantedJdItemReader;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.writer.WantedJdItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AllWantedJobScrapingJobConfig {
    private static final String JOB_NAME = "정기_원티드_채용공고_스크래핑_";
    private static final String BEAN_PREFIX = JOB_NAME + "_";

    private static final int CHUNK_SIZE = 1;

    private final FrontendWantedJdItemReader frontendWantedJdItemReader;
    private final BackendWantedJdItemReader backendWantedJdItemReader;
    private final WantedJdItemProcessor wantedJdItemProcessor;
    private final WantedJdItemWriter wantedJdItemWriter;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job wantedJdJob(JobRepository jobRepository, Step backendWantedJdStep) {
        return new JobBuilder("wantedJdJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(backendWantedJdStep(jobRepository)) // 백엔드 JD 스크래핑
            .next(frontendWantedJdStep(jobRepository)) // 프론트엔드 JD 스크래핑
            .build();
    }

    @Bean
    @JobScope
    public Step backendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder(BEAN_PREFIX + "백엔드_step", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(backendWantedJdItemReader)
            .processor(wantedJdItemProcessor)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    @JobScope
    public Step frontendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder(BEAN_PREFIX + "프론트엔드_step", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(frontendWantedJdItemReader)
            .processor(wantedJdItemProcessor)
            .writer(wantedJdItemWriter)
            .build();
    }

}
