package org.example.mq.producer;

import org.example.dto.message.OperationLogMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // Lombok注解，自动创建日志对象
@Service // 标记为服务类，交给Spring管理
@RequiredArgsConstructor // Lombok注解，生成带有final字段的构造函数
public class LogProducer {
    
    // 注入Kafka模板，用于发送消息
    // KafkaTemplate是Spring提供的工具类，简化了Kafka消息发送
    private final KafkaTemplate<String, OperationLogMessage> kafkaTemplate;
    
    // 定义Kafka主题名称
    private static final String TOPIC = "operation-logs";
    
    // 发送操作日志到Kafka
    public void sendOperationLog(OperationLogMessage message) {
        try {
            // 发送消息到指定主题
            kafkaTemplate.send(TOPIC, message);
            // 记录发送成功日志
            log.info("发送操作日志成功: {}", message);
        } catch (Exception e) {
            // 发送失败时记录错误日志
            log.error("发送操作日志失败", e);
        }
    }
}