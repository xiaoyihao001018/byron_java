package org.example.mq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.message.OperationLogMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogConsumer {
    
    @KafkaListener(topics = "operation-logs", groupId = "example-group")
    public void handleOperationLog(OperationLogMessage message) {
        try {
            log.info("收到操作日志: {}", message);
            // TODO: 保存日志到数据库
        } catch (Exception e) {
            log.error("处理操作日志失败", e);
        }
    }
} 