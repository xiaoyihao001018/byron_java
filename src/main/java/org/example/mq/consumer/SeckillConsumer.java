package org.example.mq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.message.SeckillMessage;
import org.example.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillConsumer {
    
    private final OrderService orderService;
    
    @KafkaListener(topics = "seckill-orders", groupId = "seckill-group")
    public void handleSeckillMessage(SeckillMessage message) {
        try {
            log.info("收到秒杀请求: {}", message);
            orderService.createOrder(
                message.getUserId(),
                message.getProductId(),
                message.getQuantity()
            );
            log.info("秒杀订单处理成功");
        } catch (Exception e) {
            log.error("处理秒杀订单失败", e);
        }
    }
} 
