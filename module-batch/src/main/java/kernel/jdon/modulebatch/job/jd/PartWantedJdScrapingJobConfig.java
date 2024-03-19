package kernel.jdon.modulebatch.job.jd;

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

import kernel.jdon.modulebatch.job.jd.reader.PartBackendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.PartFrontendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.writer.WantedJdItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PartWantedJdScrapingJobConfig {
    private static final String JOB_NAME = "부분_원티드_채용공고_스크래핑";
    private static final String BEAN_PREFIX = JOB_NAME + "_";

    private static final int CHUNK_SIZE = 1;

    private final PlatformTransactionManager platformTransactionManager;
    private final PartBackendWantedJdItemReader partBackendWantedJdItemReader;
    private final PartFrontendWantedJdItemReader partFrontendWantedJdItemReader;
    private final WantedJdItemWriter wantedJdItemWriter;

    @Bean(BEAN_PREFIX + "job")
    public Job partWantedJdScrapingJob(JobRepository jobRepository) {
        return new JobBuilder(BEAN_PREFIX + "job", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(partBackendWantedJdStep(jobRepository)) // 백엔드 JD 스크래핑
            .next(partFrontendWantedJdStep(jobRepository)) // 프론트엔드 JD 스크래핑
            .build();
    }

    @Bean(BEAN_PREFIX + "백엔드_step")
    @JobScope
    public Step partBackendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder(BEAN_PREFIX + "백엔드_step", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(partBackendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean(BEAN_PREFIX + "프론트엔드_step")
    @JobScope
    public Step partFrontendWantedJdStep(JobRepository jobRepository) {
        return new StepBuilder(BEAN_PREFIX + "프론트엔드_step", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(partFrontendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }
}
