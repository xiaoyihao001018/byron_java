package org.example.service;

import org.example.dto.message.SeckillMessage;
import org.example.mq.producer.SeckillProducer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillService {
    private final StringRedisTemplate redisTemplate;
    private final SeckillProducer seckillProducer;
    
    public void seckill(Long userId, Long productId, Integer quantity) {
        // 1. 检查库存
        String stockKey = "product:stock:" + productId;
        Long remainStock = redisTemplate.opsForValue().decrement(stockKey, quantity);
        
        if (remainStock < 0) {
            // 恢复库存
            redisTemplate.opsForValue().increment(stockKey, quantity);
            throw new RuntimeException("库存不足");
        }
        
        // 2. 发送消息到Kafka进行异步处理
        SeckillMessage message = new SeckillMessage(userId, productId, quantity);
        seckillProducer.sendSeckillMessage(message);
        log.info("秒杀请求已发送到消息队列");
    }
}