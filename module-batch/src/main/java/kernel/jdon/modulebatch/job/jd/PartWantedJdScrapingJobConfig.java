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

import kernel.jdon.modulebatch.job.jd.listener.PartWantedJobScrapingJobListener;
import kernel.jdon.modulebatch.job.jd.reader.PartBackendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.PartFrontendWantedJdItemReader;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.writer.WantedJdItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PartWantedJdScrapingJobConfig {

    private static final int CHUNK_SIZE = 1;

    private final PlatformTransactionManager platformTransactionManager;
    private final PartBackendWantedJdItemReader partBackendWantedJdItemReader;
    private final PartFrontendWantedJdItemReader partFrontendWantedJdItemReader;
    private final WantedJdItemWriter wantedJdItemWriter;

    /**
     * [부분_원티드_채용공고_스크래핑]
     * 직군별 최신 원티드 JD 데이터를 수집 및 적재한다.
     * 신규 JD만 insert
     */
    @Bean
    public Job partWantedJdScrapingJob(JobRepository jobRepository) {
        return new JobBuilder("partWantedJdScrapingJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(new PartWantedJobScrapingJobListener())
            .start(partBackendWantedJdScrapingStep(jobRepository)) // 백엔드 JD 스크래핑
            .next(partFrontendWantedJdScrapingStep(jobRepository)) // 프론트엔드 JD 스크래핑
            .build();
    }

    @Bean
    @JobScope
    public Step partBackendWantedJdScrapingStep(JobRepository jobRepository) {
        return new StepBuilder("partBackendWantedJdScrapingStep", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(partBackendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }

    @Bean
    @JobScope
    public Step partFrontendWantedJdScrapingStep(JobRepository jobRepository) {
        return new StepBuilder("partFrontendWantedJdScrapingStep", jobRepository)
            .<WantedJobDetailListResponse, WantedJobDetailListResponse>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(partFrontendWantedJdItemReader)
            .writer(wantedJdItemWriter)
            .build();
    }
}
