package org.example.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // Lombok注解，自动创建日志对象
@Service // 标记这是一个服务类，交给Spring管理
@RequiredArgsConstructor // Lombok注解，生成带有final字段的构造函数
public class OrderService {
    
    // 创建订单的方法
    public void createOrder(Long userId, Long productId, Integer quantity) {
        try {
            // 记录开始创建订单的日志
            log.info("开始创建订单 - 用户:{}, 商品:{}, 数量:{}", 
                userId, productId, quantity
            );
            
            // TODO: 这里需要实现实际的订单创建逻辑
            // 比如：
            // 1. 检查商品是否存在
            // 2. 检查库存
            // 3. 创建订单记录
            // 4. 扣减库存
            // 5. 可能还需要计算价格等
            
            // 记录订单创建成功的日志
            log.info("订单创建成功");
            
        } catch (Exception e) {
            // 如果创建失败，记录错误日志
            log.error("订单创建失败", e);
            // 抛出运行时异常
            throw new RuntimeException("订单创建失败", e);
        }
    }
} 