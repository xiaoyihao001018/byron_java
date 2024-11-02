package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"seckill-orders"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @BeforeEach
    void setUp() {
        // 初始化商品库存
        String stockKey = "product:stock:1";
        redisTemplate.opsForValue().set(stockKey, "10");
        log.info("初始化商品1库存为10");
    }
    
    @Test
    void testBatchSeckill() throws InterruptedException {
        try {
            // 执行秒杀，一次购买5个
            seckillService.seckill(1L, 1L, 5);
            log.info("秒测试成功");
            
            // 等待消息处理完成
            Thread.sleep(1000);
            
            // 验证库存
            String stockKey = "product:stock:1";
            String remainStock = redisTemplate.opsForValue().get(stockKey);
            log.info("剩余库存: {}", remainStock);
            
            // 添加断言
            assertEquals("5", remainStock, "库存应该减少5个");
            
        } catch (Exception e) {
            log.error("秒杀测试失败", e);
            throw e;
        }
    }
    
    @Test
    void testSeckillWithInsufficientStock() {
        try {
            // 尝试秒杀超过库存数量的商品
            seckillService.seckill(1L, 1L, 15);
            fail("应该抛出库存不足异常");
        } catch (RuntimeException e) {
            assertEquals("库存不足", e.getMessage());
            
            // 验证库存未被扣减
            String stockKey = "product:stock:1";
            String remainStock = redisTemplate.opsForValue().get(stockKey);
            assertEquals("10", remainStock, "库存不应该发生变化");
        }
    }
    
    @Test
    void testHighConcurrencySeckill() throws InterruptedException {
        // 设置较小的初始库存
        String stockKey = "product:stock:1";
        redisTemplate.opsForValue().set(stockKey, "100");
        
        // 模拟大量用户
        int threadCount = 1000;  // 1000个并发用户
        int quantityPerThread = 1;  // 每个用户购买1个
        
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        long startTime = System.currentTimeMillis();
        
        // 并发执行秒杀请求
        for (int i = 1; i <= threadCount; i++) {
            final long userId = i;
            executorService.execute(() -> {
                try {
                    seckillService.seckill(userId, 1L, quantityPerThread);
                } catch (Exception e) {
                    log.error("秒杀失败", e);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // 等待所有线程完成
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        
        long endTime = System.currentTimeMillis();
        log.info("耗时: {}ms", endTime - startTime);
        
        // 验证最终库存
        String remainStock = redisTemplate.opsForValue().get(stockKey);
        log.info("剩余库存: {}", remainStock);
        
        // 库存应该为0或大于0
        int finalStock = Integer.parseInt(remainStock);
        assertTrue(finalStock >= 0, "库存不能为负数");
    }
} 