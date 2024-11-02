package org.example.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.example.util.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀服务测试类
 * 用于测试高并发场景下的秒杀功能
 */
@Slf4j  // Lombok日志注解
@SpringBootTest  // Spring Boot测试注解
@DirtiesContext  // 指示每个测试方法后重置Spring上下文
@EmbeddedKafka(partitions = 1, topics = {"seckill-orders"})  // 使用嵌入式Kafka进行测试
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;  // 秒杀服务
    
    @Autowired
    private StringRedisTemplate redisTemplate;  // Redis模板
    
    @Autowired
    private ExecutorService executorService;  // 线程池
    
    // Redis中商品库存的key
    private static final String STOCK_KEY = "product:stock:1";
    // 成功计数器
    private static final AtomicInteger successCount = new AtomicInteger(0);
    // 失败计数器
    private static final AtomicInteger failCount = new AtomicInteger(0);
    
    /**
     * 每个测试方法执行前的准备工作
     * 初始化Redis中的库存数据和计数器
     */
    @BeforeEach
    void setUp() {
        // 设置初始库存为100
        redisTemplate.opsForValue().set(STOCK_KEY, "100");
        // 重置计数器
        successCount.set(0);
        failCount.set(0);
        log.info("初始化测试环境完成");
    }
    
    /**
     * 测试高并发秒杀场景
     * 模拟1000个用户同时进行秒杀
     */
    @Test
    void testHighConcurrencySeckill() throws InterruptedException {
        int threadCount = 1000;  // 并发线程数
        CountDownLatch latch = new CountDownLatch(threadCount);  // 用于等待所有线程完成
        long startTime = System.currentTimeMillis();  // 记录开始时间
        
        // 并行执行秒杀请求
        IntStream.rangeClosed(1, threadCount).parallel().forEach(i -> {
            try {
                // 执行秒杀
                seckillService.seckill((long)i, 1L, 1);
                successCount.incrementAndGet();  // 成功计数加1
            } catch (Exception e) {
                failCount.incrementAndGet();  // 失败计数加1
                log.debug("秒杀失败: {}", e.getMessage());
            } finally {
                latch.countDown();  // 计数器减1
            }
        });
        
        // 等待所有线程完成或超时
        latch.await(10, TimeUnit.SECONDS);
        
        // 验证最终库存
        TestHelper.validateStock(redisTemplate, STOCK_KEY, 0);
        // 打印测试指标
        TestHelper.printTestMetrics(startTime, successCount.get(), failCount.get());
    }
} 