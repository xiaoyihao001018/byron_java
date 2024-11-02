package org.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试辅助工具类
 * 提供测试验证和指标统计的通用方法
 */
@Slf4j
public class TestHelper {
    
    /**
     * 验证Redis中的库存数量
     * 检查库存是否符合预期，并确保不为负数
     *
     * @param redisTemplate Redis操作模板
     * @param stockKey 库存的Redis键
     * @param expectedStock 预期的库存数量
     * @throws AssertionError 当验证失败时抛出
     */
    public static void validateStock(StringRedisTemplate redisTemplate, 
                                   String stockKey, 
                                   int expectedStock) {
        String remainStock = redisTemplate.opsForValue().get(stockKey);
        int actualStock = Integer.parseInt(remainStock);
        log.info("当前库存: {}", actualStock);
        assertTrue(actualStock >= 0, "库存不能为负数");
        assertEquals(expectedStock, actualStock, "库存数量不符合预期");
    }
    
    /**
     * 打印测试指标统计
     * 包括总耗时、成功/失败请求数和TPS
     *
     * @param startTime 测试开始时间（毫秒）
     * @param successCount 成功请求数
     * @param failCount 失败请求数
     */
    public static void printTestMetrics(long startTime, int successCount, int failCount) {
        long endTime = System.currentTimeMillis();
        log.info("测试结果统计:");
        log.info("总耗时: {}ms", endTime - startTime);
        log.info("成功请求数: {}", successCount);
        log.info("失败请求数: {}", failCount);
        log.info("TPS: {}", (successCount * 1000.0) / (endTime - startTime));
    }
} 