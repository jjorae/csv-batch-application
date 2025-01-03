package me.jjorae.csv_batch_application.config;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;

@Configuration
public class ItemReaderConfig {
    @Bean
    @StepScope
    public FlatFileItemReader<GeneralRestaurantRawData> reader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        return new FlatFileItemReaderBuilder<GeneralRestaurantRawData>()
            .name("fileItemReader")
            .resource(
                StringUtils.hasLength(inputFile) ? 
                new FileSystemResource(inputFile)
                : new ClassPathResource("sample-data.csv")
            )
            .linesToSkip(1)
            .encoding("EUC-KR")
            .delimited()
            .strict(false) // CSV 마지막 콤마(,) 무시하기 위해 설정
            .names(getHeaders())
            .fieldSetMapper(new BeanWrapperFieldSetMapper<GeneralRestaurantRawData>() {{
                setTargetType(GeneralRestaurantRawData.class);
            }})
            .build();
    }

    private String[] getHeaders() {
        return Arrays.stream(GeneralRestaurantRawData.class.getDeclaredFields())
            .map(Field::getName)
            .toArray(String[]::new);
    }
}
