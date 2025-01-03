# CSV 배치 처리 애플리케이션

CSV 데이터를 읽어서 데이터베이스에 저장하는 Spring Batch 애플리케이션입니다.

## 기능

- CSV 파일 읽기
    - [공공데이터포털(전국일반음식점표준데이터)](https://www.data.go.kr/data/15096283/standard.do) 파일을 기준으로 구현되었습니다.
- 데이터 유효성 검사 및 형식 변환
- 데이터베이스 일괄 저장
- 오류 데이터 건너뛰기 및 로깅
- 처리 현황 모니터링

## 기술 스택

- Java 17
- Spring Boot 3.4.1
- Spring Batch
- MySQL 8
- HSQLDB (테스트용 내장 DB)
- Gradle
- Lombok

## 프로젝트 구조

```
src/main/java/me/jjorae/csv_batch_application/
├── config/
│ ├── BatchConfig.java # 배치 Job/Step 설정
│ ├── ItemReaderConfig.java # ItemReader 설정
│ ├── ItemProcessorConfig.java # ItemProcessor 설정
│ ├── ItemWriterConfig.java # ItemWriter 설정
│ └── listener/ # 각종 리스너 설정
├── dto/ # 데이터 객체
└── exception/ # 커스텀 예외 클래스
```

## 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew clean build
```

### 2. 애플리케이션 실행
```bash
java -jar build/libs/csv-batch-application-0.0.1.jar inputFile=/path/to/your/csv/file
```

#### 실행 옵션

- `inputFile`: CSV 파일 경로 (미지정시 classpath의 sample-data.csv 사용)
- `batch.chunk-size`: chunk 크기 (기본값: 1000)
- `batch.skip-limit`: 최대 건너뛰기 횟수 (기본값: 1000)

## 데이터베이스 스키마

데이터베이스 테이블은 애플리케이션 시작 시 자동으로 생성됩니다. 스키마 정의는 다음 파일을 참고하세요:

`src/main/resources/schema-all.sql`

배치용 테이블은 아래 옵션을 통해 항상 생성하도록 설정되어 있습니다. 상황에 맞게 `always`, `embeded`, `never`로 변경하여 사용하면 됩니다.

```
spring.batch.jdbc.initialize-schema=always
```


## 에러 처리

- 데이터 파싱 오류(`ParsingException`)와 유효성 검사 오류(`ValidationException`)는 자동으로 건너뛰기 처리됩니다.
- 오류 발생 시 로그에 상세 내용이 기록됩니다.
- `CustomSkipListener`를 통해 건너뛴 아이템에 대한 추가 처리가 가능합니다.

## 모니터링

처리 현황은 로그를 통해 확인할 수 있습니다:
- 처리된 아이템 수
- 필터링된 아이템 수
- 건너뛴 아이템 수
- 에러 발생 내역
