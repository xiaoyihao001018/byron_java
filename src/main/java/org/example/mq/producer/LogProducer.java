package org.example.mq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.message.OperationLogMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogProducer {
    
    private final KafkaTemplate<String, OperationLogMessage> kafkaTemplate;
    private static final String TOPIC = "operation-logs";
    
    public void sendOperationLog(OperationLogMessage message) {
        try {
            kafkaTemplate.send(TOPIC, message);
            log.info("发送操作日志成功: {}", message);
        } catch (Exception e) {
            log.error("发送操作日志失败", e);
        }
    }
} 