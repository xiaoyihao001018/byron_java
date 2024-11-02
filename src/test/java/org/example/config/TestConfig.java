package org.example.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class TestConfig {
    
    @Bean
    public ExecutorService testExecutor() {
        return new ThreadPoolExecutor(
            200,  // 核心线程数
            1000, // 最大线程数 
            60L, // 空闲线程存活时间
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000), // 使用有界队列
            new ThreadFactoryBuilder()
                .setNameFormat("seckill-test-pool-%d")
                .build(),
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );
    }
} 