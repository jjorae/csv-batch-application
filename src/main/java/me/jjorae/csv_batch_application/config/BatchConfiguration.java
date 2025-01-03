package me.jjorae.csv_batch_application.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import me.jjorae.csv_batch_application.config.ItemProcessorConfig.CsvItemProcessor;
import me.jjorae.csv_batch_application.config.listener.CustomSkipListener;
import me.jjorae.csv_batch_application.config.listener.JobCompletionNotificationListener;
import me.jjorae.csv_batch_application.config.listener.StepExceptionListener;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantData;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;
import me.jjorae.csv_batch_application.exception.ParsingException;
import me.jjorae.csv_batch_application.exception.ValidationException;

@Configuration
public class BatchConfiguration {
    @Bean
    public Job job(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("job1", jobRepository)
            .listener(listener)
            .start(step1)
            .build();
    }

    @Bean
    public Step step1(
        JobRepository jobRepository, 
        DataSourceTransactionManager transactionManager, 
        FlatFileItemReader<GeneralRestaurantRawData> reader, 
        CsvItemProcessor processor, 
        JdbcBatchItemWriter<GeneralRestaurantData> writer,
        StepExceptionListener stepExceptionListener,
        CustomSkipListener customSkipListener,
        @Value("${batch.skip-limit:10}") int skipLimit
    ) {
        return new StepBuilder("step1", jobRepository)
            .<GeneralRestaurantRawData, GeneralRestaurantData> chunk(100, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .faultTolerant()
            .skipLimit(skipLimit)
            .skip(ValidationException.class)
            .skip(ParsingException.class)
            .listener(stepExceptionListener)
            .listener(customSkipListener)
            .build();
    }
}
