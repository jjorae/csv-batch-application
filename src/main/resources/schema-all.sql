DROP TABLE general_restaurant_data IF EXISTS;

CREATE TABLE general_restaurant_data (
    id BIGINT PRIMARY KEY,
    service_name VARCHAR(50),
    service_id VARCHAR(20),
    local_gov_code VARCHAR(10),
    management_num VARCHAR(30),
    license_date DATE,
    license_cancel_date DATE,
    business_status_code VARCHAR(2),
    business_status_name VARCHAR(20),
    detailed_status_code VARCHAR(2),
    detailed_status_name VARCHAR(20),
    closure_date DATE,
    temp_closure_start_date DATE,
    temp_closure_end_date DATE,
    reopen_date DATE,
    phone_number VARCHAR(20),
    area DECIMAL(10,2),
    zip_code VARCHAR(10),
    address VARCHAR(200),
    road_address VARCHAR(200),
    road_zip_code VARCHAR(10),
    business_name VARCHAR(100),
    last_modified_datetime DATETIME,
    data_update_type VARCHAR(1),
    data_update_date DATETIME,
    business_type VARCHAR(50),
    coordinate_x DECIMAL(18,9),
    coordinate_y DECIMAL(18,9),
    sanitation_business_type VARCHAR(50),
    male_employee_count INT,
    female_employee_count INT,
    location_characteristics VARCHAR(100),
    grade_classification VARCHAR(50),
    water_supply_type VARCHAR(50),
    total_employee_count INT,
    head_office_employee_count INT,
    factory_office_employee_count INT,
    factory_sales_employee_count INT,
    factory_production_employee_count INT,
    building_ownership_type VARCHAR(50),
    guarantee_amount DECIMAL(15,2),
    monthly_rent DECIMAL(15,2),
    multiple_use_business VARCHAR(1),
    total_facility_size DECIMAL(10,2),
    traditional_business_num VARCHAR(50),
    traditional_main_food VARCHAR(100),
    website VARCHAR(200)
);