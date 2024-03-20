package kernel.jdon.modulebatch.job.course;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.job.course.dto.InflearnCourseAndSkillKeywordInfo;
import kernel.jdon.modulebatch.job.course.processor.InflearnCourseItemProcessor;
import kernel.jdon.modulebatch.job.course.reader.InflearnCourseItemReader;
import kernel.jdon.modulebatch.job.course.writer.InflearnCourseItemWriter;
import kernel.jdon.modulebatch.listener.InflearnJobExecutionListener;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InflearnCourseScrapingJobConfig {

    private final InflearnJobExecutionListener inflearnJobExecutionListener;
    private final InflearnCourseItemReader inflearnCourseItemReader;
    private final InflearnCourseItemProcessor inflearnCourseItemProcessor;
    private final InflearnCourseItemWriter inflearnCourseItemWriter;

    @Bean(name = "inflearnCrawlJob")
    public Job inflearnCrawlJob(JobRepository jobRepository, @Qualifier("inflearnCourseStep") Step inflearnCourseStep) {
        return new JobBuilder("inflearnCrawlJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(inflearnJobExecutionListener)
            .start(inflearnCourseStep)
            .build();
    }

    @Bean(name = "inflearnCourseStep")
    @JobScope
    public Step inflearnCourseStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("inflearnCourseStep", jobRepository)
            .<String, InflearnCourseAndSkillKeywordInfo>chunk(3, transactionManager)
            .reader(inflearnCourseItemReader)
            .processor(inflearnCourseItemProcessor)
            .writer(inflearnCourseItemWriter)
            .allowStartIfComplete(true)
            .build();
    }
}
