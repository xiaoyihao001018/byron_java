package org.example.mq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.message.SeckillMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillProducer {
    
    private final KafkaTemplate<String, SeckillMessage> kafkaTemplate;
    private static final String TOPIC = "seckill-orders";
    
    public void sendSeckillMessage(SeckillMessage message) {
        try {
            kafkaTemplate.send(TOPIC, message);
            log.info("发送秒杀消息成功: {}", message);
        } catch (Exception e) {
            log.error("发送秒杀消息失败", e);
            throw new RuntimeException("发送秒杀消息失败", e);
        }
    }
} 