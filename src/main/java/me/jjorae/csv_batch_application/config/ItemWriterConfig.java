package me.jjorae.csv_batch_application.config;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.jjorae.csv_batch_application.dto.GeneralRestaurantData;

@Configuration
public class ItemWriterConfig {
    @Bean
    public JdbcBatchItemWriter<GeneralRestaurantData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<GeneralRestaurantData>()
            .sql("INSERT INTO general_restaurant_data (" +
                "id, service_name, service_id, local_gov_code, management_num, " +
                "license_date, license_cancel_date, business_status_code, business_status_name, " +
                "detailed_status_code, detailed_status_name, closure_date, temp_closure_start_date, " +
                "temp_closure_end_date, reopen_date, phone_number, area, zip_code, address, " +
                "road_address, road_zip_code, business_name, last_modified_datetime, " +
                "data_update_type, data_update_date, business_type, coordinate_x, coordinate_y, " +
                "sanitation_business_type, male_employee_count, female_employee_count, " +
                "location_characteristics, grade_classification, water_supply_type, " +
                "total_employee_count, head_office_employee_count, factory_office_employee_count, " +
                "factory_sales_employee_count, factory_production_employee_count, " +
                "building_ownership_type, guarantee_amount, monthly_rent, multiple_use_business, " +
                "total_facility_size, traditional_business_num, traditional_main_food, website) " +
                "VALUES (" +
                ":id, :serviceName, :serviceId, :localGovCode, :managementNum, " +
                ":licenseDate, :licenseCancelDate, :businessStatusCode, :businessStatusName, " +
                ":detailedStatusCode, :detailedStatusName, :closureDate, :tempClosureStartDate, " +
                ":tempClosureEndDate, :reopenDate, :phoneNumber, :area, :zipCode, :address, " +
                ":roadAddress, :roadZipCode, :businessName, :lastModifiedDatetime, " +
                ":dataUpdateType, :dataUpdateDate, :businessType, :coordinateX, :coordinateY, " +
                ":sanitationBusinessType, :maleEmployeeCount, :femaleEmployeeCount, " +
                ":locationCharacteristics, :gradeClassification, :waterSupplyType, " +
                ":totalEmployeeCount, :headOfficeEmployeeCount, :factoryOfficeEmployeeCount, " +
                ":factorySalesEmployeeCount, :factoryProductionEmployeeCount, " +
                ":buildingOwnershipType, :guaranteeAmount, :monthlyRent, :multipleUseBusiness, " +
                ":totalFacilitySize, :traditionalBusinessNum, :traditionalMainFood, :website)")
            .dataSource(dataSource)
            .beanMapped()
            .build();
    }
}
