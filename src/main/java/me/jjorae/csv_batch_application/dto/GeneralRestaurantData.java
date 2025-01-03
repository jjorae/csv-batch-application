package me.jjorae.csv_batch_application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class GeneralRestaurantData {
    private Long id;
    private String serviceName;
    private String serviceId;
    private String localGovCode;
    private String managementNum;
    private LocalDate licenseDate;
    private LocalDate licenseCancelDate;
    private String businessStatusCode;
    private String businessStatusName;
    private String detailedStatusCode;
    private String detailedStatusName;
    private LocalDate closureDate;
    private LocalDate tempClosureStartDate;
    private LocalDate tempClosureEndDate;
    private LocalDate reopenDate;
    private String phoneNumber;
    private BigDecimal area;
    private String zipCode;
    private String address;
    private String roadAddress;
    private String roadZipCode;
    private String businessName;
    private LocalDateTime lastModifiedDatetime;
    private String dataUpdateType;
    private LocalDateTime dataUpdateDate;
    private String businessType;
    private BigDecimal coordinateX;
    private BigDecimal coordinateY;
    private String sanitationBusinessType;
    private Integer maleEmployeeCount;
    private Integer femaleEmployeeCount;
    private String locationCharacteristics;
    private String gradeClassification;
    private String waterSupplyType;
    private Integer totalEmployeeCount;
    private Integer headOfficeEmployeeCount;
    private Integer factoryOfficeEmployeeCount;
    private Integer factorySalesEmployeeCount;
    private Integer factoryProductionEmployeeCount;
    private String buildingOwnershipType;
    private BigDecimal guaranteeAmount;
    private BigDecimal monthlyRent;
    private String multipleUseBusiness;
    private BigDecimal totalFacilitySize;
    private String traditionalBusinessNum;
    private String traditionalMainFood;
    private String website;
}
