package kernel.jdon.modulebatch.job.course;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.job.course.dto.InflearnCourseAndSkillKeywordInfo;
import kernel.jdon.modulebatch.job.course.listener.InflearnJobExecutionListener;
import kernel.jdon.modulebatch.job.course.processor.InflearnCourseItemProcessor;
import kernel.jdon.modulebatch.job.course.reader.InflearnCourseItemReader;
import kernel.jdon.modulebatch.job.course.writer.InflearnCourseItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InflearnCourseScrapingJobConfig {

    private static final int CHUNK_SIZE = 1;
    private static final String JOB_NAME = "인프런_강의_스크래핑";
    private static final String BEAN_PREFIX = JOB_NAME + "_";

    private final PlatformTransactionManager platformTransactionManager;
    private final InflearnJobExecutionListener inflearnJobExecutionListener;
    private final InflearnCourseItemReader inflearnCourseItemReader;
    private final InflearnCourseItemProcessor inflearnCourseItemProcessor;
    private final InflearnCourseItemWriter inflearnCourseItemWriter;

    @Bean(name = BEAN_PREFIX + "job")
    public Job inflearnCrawlJob(JobRepository jobRepository) {
        return new JobBuilder("inflearnCrawlJob", jobRepository)
            .listener(inflearnJobExecutionListener)
            .start(inflearnCourseStep(jobRepository))
            .build();
    }

    @Bean(name = BEAN_PREFIX + "step")
    @JobScope
    public Step inflearnCourseStep(JobRepository jobRepository) {
        return new StepBuilder("inflearnCourseStep", jobRepository)
            .<String, InflearnCourseAndSkillKeywordInfo>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(inflearnCourseItemReader)
            .processor(inflearnCourseItemProcessor)
            .writer(inflearnCourseItemWriter)
            .allowStartIfComplete(true)
            .build();
    }
}
