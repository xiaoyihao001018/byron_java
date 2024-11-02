package org.example.mq.consumer;

import org.example.dto.message.SeckillMessage;
import org.example.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀消息消费者
 * 负责消费Kafka中的秒杀消息，异步处理秒杀订单
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillConsumer {
    
    private final OrderService orderService;
    
    /**
     * 监听秒杀消息队列,处理秒杀订单
     * 使用@KafkaListener注解监听指定的topic
     * 
     * @param message 秒杀消息，包含用户ID、商品ID和购买数量
     */
    @KafkaListener(topics = "seckill-orders", groupId = "seckill-group")
    public void handleSeckillMessage(SeckillMessage message) {
        try {
            log.info("收到秒杀请求: {}", message);
            // 调用订单服务创建订单
            orderService.createOrder(
                message.getUserId(),
                message.getProductId(),
                message.getQuantity()
            );
            log.info("秒杀订单处理成功");
        } catch (Exception e) {
            log.error("处理秒杀订单失败", e);
            // 这里可以添加重试逻辑或者将失败消息写入死信队列
        }
    }
} 
