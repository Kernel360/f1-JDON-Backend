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
import kernel.jdon.modulebatch.jd.reader.WantedJdItemReader;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.search.JobSearchJobPosition;
import kernel.jdon.modulebatch.jd.writer.WantedJdItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WantedJdJobConfig {
    private static final int CHUNK_SIZE = 3;
    private final WantedJdItemReader wantedJdItemReader;
    private final WantedJdItemProcessor wantedJdItemProcessor;
    private final WantedJdItemWriter wantedJdItemWriter;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job wantedJdJob(JobRepository jobRepository, Step backendWantedJdStep) {
        return new JobBuilder("wantedJdJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(backendWantedJdStep(jobRepository)) // 백엔드 스크래핑
            .next(frontendWantedJdStep(jobRepository)) // 프론트엔드 스크래핑
            .build();
    }

    @Bean
    @JobScope
    public Step backendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder("backendWantedJdStep", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(backendWantedJdItemReader())
            .processor(wantedJdItemProcessor)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    @JobScope
    public Step frontendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder("frontendWantedJdStep", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(frontendWantedJdItemReader())
            .processor(wantedJdItemProcessor)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    public WantedJdItemReader backendWantedJdItemReader() {
        JobSearchJobPosition backendJobPosition = JobSearchJobPosition.JOB_POSITION_SERVER;
        // todo : 여기에 어떻게 JobSearchJobPosition.JOB_POSITION_SERVER를 전달할 수 있을까요?
        return new WantedJdItemReader(null, null, null);
    }

    @Bean
    public WantedJdItemReader frontendWantedJdItemReader() {
        JobSearchJobPosition frontendJobPosition = JobSearchJobPosition.JOB_POSITION_FRONTEND;
        // todo : 여기에 어떻게 JobSearchJobPosition.JOB_POSITION_SERVER를 전달할 수 있을까요?
        return new WantedJdItemReader(null, null, null);
    }

}
