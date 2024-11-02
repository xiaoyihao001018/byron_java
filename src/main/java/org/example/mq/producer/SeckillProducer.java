package org.example.mq.producer;

import org.example.dto.message.SeckillMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀消息生产者
 * 负责将秒杀请求发送到Kafka消息队列中
 */
@Slf4j  // Lombok注解，自动创建SLF4J日志对象
@Service // Spring注解，标记为服务组件
@RequiredArgsConstructor // Lombok注解，生成带有final字段的构造函数
public class SeckillProducer {
    
    // 注入Kafka模板,用于发送消息
    private final KafkaTemplate<String, SeckillMessage> kafkaTemplate;
    
    // 定义topic名称
    private static final String TOPIC = "seckill-orders";
    
    /**
     * 发送秒杀消息到Kafka
     * 将秒杀请求异步发送到消息队列，实现流量削峰
     * 
     * @param message 秒杀消息对象,包含用户ID、商品ID、数量等信息
     * @throws RuntimeException 当消息发送失败时抛出异常
     */
    public void sendSeckillMessage(SeckillMessage message) {
        try {
            // 发送消息到指定topic
            kafkaTemplate.send(TOPIC, message);
            log.info("发送秒杀消息成功: {}", message);
        } catch (Exception e) {
            log.error("发送秒杀消息失败", e);
            throw new RuntimeException("发送秒杀消息失败", e);
        }
    }
} 