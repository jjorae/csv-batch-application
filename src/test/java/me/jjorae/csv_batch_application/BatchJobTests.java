package me.jjorae.csv_batch_application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import me.jjorae.csv_batch_application.config.ItemProcessorConfig;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantData;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;
import me.jjorae.csv_batch_application.exception.ParsingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBatchTest
@SpringBootTest
@EnableAutoConfiguration
@SpringJUnitConfig
public class BatchJobTests {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
  
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemProcessorConfig.CsvItemProcessor processor;

    @Value("${batch.skip-limit:1000}") int skipLimit;

    @BeforeEach
    void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
        jdbcTemplate.execute("DELETE FROM general_restaurant_data");
    }

    @Test
    void testJobExecution() throws Exception {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
    
        // then
        assertThat(actualJobInstance.getJobName()).isEqualTo("job1");
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM general_restaurant_data", Integer.class);

        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testDataProcessing() throws Exception {
        // given
        GeneralRestaurantRawData rawData = new GeneralRestaurantRawData();
        rawData.setId(1L);
        rawData.setBusinessName("테스트 식당");
        rawData.setLicenseDate("2025-01-01");
        rawData.setArea("100.5");
        
        // when
        GeneralRestaurantData processedData = processor.process(rawData);
        
        // then
        assertThat(processedData).isNotNull();
        assertThat(processedData.getId()).isEqualTo(1L);
        assertThat(processedData.getBusinessName()).isEqualTo("테스트 식당");
        assertThat(processedData.getLicenseDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(processedData.getArea()).isEqualByComparingTo(new BigDecimal("100.5"));
    }

    @Test
    void testInvalidDateProcessing() {
        // given
        GeneralRestaurantRawData rawData = new GeneralRestaurantRawData();
        rawData.setId(1L);
        rawData.setLicenseDate("0000-00-00"); // 잘못된-날짜-형식
        
        // when & then
        assertThatThrownBy(() -> processor.process(rawData))
            .isInstanceOf(ParsingException.class);
    }

    @Test
    void testSkipLimit() throws Exception {
        // given
        String testFilePath = new ClassPathResource("sample-wrong-data.csv").getFile().getAbsolutePath();
        JobParameters params = new JobParametersBuilder()
            .addString("inputFile", testFilePath)
            .toJobParameters();
        
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(params);
        
        // then
        assertThat(jobExecution.getExitStatus().getExitCode())
            .isEqualTo("COMPLETED");
            
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        
        assertThat(stepExecution.getSkipCount()).isGreaterThan(0);
        
        int validRecordCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM general_restaurant_data", 
            Integer.class
        );
        assertThat(validRecordCount).isEqualTo(7); // 샘플 데이터 9개 중 오류 데이터 2개
    }
}
