package org.example.service;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    public void createOrder(Long userId, Long productId, Integer quantity) {
        try {
            log.info("开始创建订单 - 用户:{}, 商品:{}, 数量:{}", 
                userId, productId, quantity
            );
            // TODO: 实现订单创建逻辑
            
            log.info("订单创建成功");
        } catch (Exception e) {
            log.error("订单创建失败", e);
            throw new RuntimeException("订单创建失败", e);
        }
    }
} 