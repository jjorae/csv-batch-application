package me.jjorae.csv_batch_application.config;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;

@Configuration
public class ItemReaderConfig {
    @Bean
    public FlatFileItemReader<GeneralRestaurantRawData> reader() {
        return new FlatFileItemReaderBuilder<GeneralRestaurantRawData>()
            .name("fileItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
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
