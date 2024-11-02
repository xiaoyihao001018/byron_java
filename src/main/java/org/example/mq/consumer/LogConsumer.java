package org.example.mq.consumer;

import org.example.dto.message.OperationLogMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // Lombok注解，自动创建日志对象
@Service // 标记这是一个服务类，交给Spring管理
@RequiredArgsConstructor // Lombok注解，生成带有final字段的构造函数
public class LogConsumer {
    
    // @KafkaListener注解标记这是一个Kafka消息监听方法
    // topics: 监听的主题名称
    // groupId: 消费者组ID，同一组的消费者不会重复消费同一条消息
    @KafkaListener(topics = "operation-logs", groupId = "example-group")
    public void handleOperationLog(OperationLogMessage message) {
        try {
            // 记录收到的日志消息
            log.info("收到操作日志: {}", message);
            // TODO: 这里可以添加保存日志到数据库的逻辑
        } catch (Exception e) {
            // 如果处理失败，记录错误日志
            log.error("处理操作日志失败", e);
        }
    }
}