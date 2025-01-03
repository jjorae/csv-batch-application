package me.jjorae.csv_batch_application.dto;

import lombok.Data;

@Data // BeanWrapperFieldSetMapper 사용하려면 Setter 필요
public class GeneralRestaurantRawData {
    private Long id;
    private String serviceName;
    private String serviceId;
    private String localGovCode;
    private String managementNum;
    private String licenseDate;
    private String licenseCancelDate;
    private String businessStatusCode;
    private String businessStatusName;
    private String detailedStatusCode;
    private String detailedStatusName;
    private String closureDate;
    private String tempClosureStartDate;
    private String tempClosureEndDate;
    private String reopenDate;
    private String phoneNumber;
    private String area;
    private String zipCode;
    private String address;
    private String roadAddress;
    private String roadZipCode;
    private String businessName;
    private String lastModifiedDatetime;
    private String dataUpdateType;
    private String dataUpdateDate;
    private String businessType;
    private String coordinateX;
    private String coordinateY;
    private String sanitationBusinessType;
    private String maleEmployeeCount;
    private String femaleEmployeeCount;
    private String locationCharacteristics;
    private String gradeClassification;
    private String waterSupplyType;
    private String totalEmployeeCount;
    private String headOfficeEmployeeCount;
    private String factoryOfficeEmployeeCount;
    private String factorySalesEmployeeCount;
    private String factoryProductionEmployeeCount;
    private String buildingOwnershipType;
    private String guaranteeAmount;
    private String monthlyRent;
    private String multipleUseBusiness;
    private String totalFacilitySize;
    private String traditionalBusinessNum;
    private String traditionalMainFood;
    private String website;
}
