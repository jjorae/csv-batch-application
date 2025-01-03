package me.jjorae.csv_batch_application.config.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepExceptionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step '{}' 시작", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step '{}' 종료. 처리 결과:", stepExecution.getStepName());
        log.info("읽은 항목 수: {}", stepExecution.getReadCount());
        log.info("필터된 항목 수: {}", stepExecution.getFilterCount());
        log.info("쓰기 성공 수: {}", stepExecution.getWriteCount());
        log.info("건너뛴 항목 수: {}", stepExecution.getSkipCount());
        
        if (stepExecution.getFailureExceptions().size() > 0) {
            log.error("Step 실행 중 발생한 예외들:");
            stepExecution.getFailureExceptions()
                .forEach(throwable -> log.error("예외 발생: ", throwable));
        }
        
        return stepExecution.getExitStatus();
    }
}
