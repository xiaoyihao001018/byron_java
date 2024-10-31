package org.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis测试类
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisConnection() {
        // 1. 测试Redis连接
        try {
            // 存储数据
            String key = "test:hello";
            String value = "Hello Redis!";
            redisTemplate.opsForValue().set(key, value);
            log.info("存储数据成功: key={}, value={}", key, value);

            // 获取数据
            Object result = redisTemplate.opsForValue().get(key);
            log.info("获取数据成功: key={}, value={}", key, result);

           

        } catch (Exception e) {
            log.error("Redis测试失败", e);
            throw e;
        }
    }
} 