package me.jjorae.csv_batch_application.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantData;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;
import me.jjorae.csv_batch_application.exception.ParsingException;

@Configuration
public class ItemProcessorConfig {
    @Bean
    public CsvItemProcessor processor() {
        return new CsvItemProcessor();
    }

    @Slf4j
    public static class CsvItemProcessor implements ItemProcessor<GeneralRestaurantRawData, GeneralRestaurantData> {
        
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public GeneralRestaurantData process(GeneralRestaurantRawData item) throws Exception {
            // Raw 데이터를 가져와 데이터 변환
            GeneralRestaurantData data = GeneralRestaurantData.builder()
                .id(item.getId())
                .serviceName(item.getServiceName())
                .serviceId(item.getServiceId())
                .localGovCode(item.getLocalGovCode())
                .managementNum(item.getManagementNum())
                .licenseDate(parseLocalDate(item.getLicenseDate()))
                .licenseCancelDate(parseLocalDate(item.getLicenseCancelDate()))
                .businessStatusCode(item.getBusinessStatusCode())
                .businessStatusName(item.getBusinessStatusName())
                .detailedStatusCode(item.getDetailedStatusCode())
                .detailedStatusName(item.getDetailedStatusName())
                .closureDate(parseLocalDate(item.getClosureDate()))
                .tempClosureStartDate(parseLocalDate(item.getTempClosureStartDate()))
                .tempClosureEndDate(parseLocalDate(item.getTempClosureEndDate()))
                .reopenDate(parseLocalDate(item.getReopenDate()))
                .phoneNumber(item.getPhoneNumber())
                .area(parseNullableBigDecimal(item.getArea()))
                .zipCode(item.getZipCode())
                .address(item.getAddress())
                .roadAddress(item.getRoadAddress())
                .roadZipCode(item.getRoadZipCode())
                .businessName(item.getBusinessName())
                .lastModifiedDatetime(parseLocalDateTime(item.getLastModifiedDatetime()))
                .dataUpdateType(item.getDataUpdateType())
                .dataUpdateDate(parseLocalDateTime(item.getDataUpdateDate()))
                .businessType(item.getBusinessType())
                .coordinateX(parseNullableBigDecimal(item.getCoordinateX()))
                .coordinateY(parseNullableBigDecimal(item.getCoordinateY()))
                .sanitationBusinessType(item.getSanitationBusinessType())
                .maleEmployeeCount(parseNullableInteger(item.getMaleEmployeeCount()))
                .femaleEmployeeCount(parseNullableInteger(item.getFemaleEmployeeCount()))
                .locationCharacteristics(item.getLocationCharacteristics())
                .gradeClassification(item.getGradeClassification())
                .waterSupplyType(item.getWaterSupplyType())
                .totalEmployeeCount(parseNullableInteger(item.getTotalEmployeeCount()))
                .headOfficeEmployeeCount(parseNullableInteger(item.getHeadOfficeEmployeeCount()))
                .factoryOfficeEmployeeCount(parseNullableInteger(item.getFactoryOfficeEmployeeCount()))
                .factorySalesEmployeeCount(parseNullableInteger(item.getFactorySalesEmployeeCount()))
                .factoryProductionEmployeeCount(parseNullableInteger(item.getFactoryProductionEmployeeCount()))
                .buildingOwnershipType(item.getBuildingOwnershipType())
                .guaranteeAmount(parseNullableBigDecimal(item.getGuaranteeAmount()))
                .monthlyRent(parseNullableBigDecimal(item.getMonthlyRent()))
                .multipleUseBusiness(item.getMultipleUseBusiness())
                .totalFacilitySize(parseNullableBigDecimal(item.getTotalFacilitySize()))
                .traditionalBusinessNum(item.getTraditionalBusinessNum())
                .traditionalMainFood(item.getTraditionalMainFood())
                .website(item.getWebsite())
                .build();
            log.debug("Converting raw data. {} => {}", item, data);
            return data;
        }

        private LocalDate parseLocalDate(String value) {
            if (!StringUtils.hasText(value)) {
                return null;
            }
            try {
                return LocalDate.parse(value.trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                log.error("Failed to parse date: {}", value);
                throw new ParsingException(value, e);
            }
        }

        private LocalDateTime parseLocalDateTime(String value) {
            if (!StringUtils.hasText(value)) {
                return null;
            }
            try {
                return LocalDateTime.parse(value.trim(), DATETIME_FORMATTER);
            } catch (DateTimeParseException e) {
                log.warn("Failed to parse datetime: {}", value);
                throw new ParsingException(value, e);
            }
        }

        private Integer parseNullableInteger(String value) {
            if (!StringUtils.hasText(value)) {
                return null;
            }
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                log.error("Failed to parse integer: {}", value);
                throw new ParsingException(value, e);
            }
        }

        private BigDecimal parseNullableBigDecimal(String value) {
            if (!StringUtils.hasText(value)) {
                return null;
            }
            try {
                return new BigDecimal(value.trim());
            } catch (NumberFormatException e) {
                log.error("Failed to parse decimal: {}", value);
                throw new ParsingException(value, e);
            }
        }
    }
}
