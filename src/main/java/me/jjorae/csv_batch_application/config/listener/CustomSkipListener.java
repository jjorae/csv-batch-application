package me.jjorae.csv_batch_application.config.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantData;
import me.jjorae.csv_batch_application.dto.GeneralRestaurantRawData;

@Slf4j
@Component
public class CustomSkipListener implements SkipListener<GeneralRestaurantRawData, GeneralRestaurantData> {
    /*
     * 필요한 경우 실패한 데이터를 별도 저장하거나 알림 발송
     */
    @Override
    public void onSkipInRead(Throwable t) {
        log.error("데이터 읽기 중 오류 발생으로 건너뜀: ", t);
    }

    @Override
    public void onSkipInProcess(GeneralRestaurantRawData item, Throwable t) {
        log.error("데이터 처리 중 오류 발생으로 건너뜀. 데이터: {}, 예외: ", item, t);
    }

    @Override
    public void onSkipInWrite(GeneralRestaurantData item, Throwable t) {
        log.error("데이터 쓰기 중 오류 발생으로 건너뜀. 데이터: {}, 예외: ", item, t);
    }
}
