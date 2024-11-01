package org.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.message.OperationLogMessage;
import org.example.mq.producer.LogProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class KafkaIntegrationTest {

    @Autowired
    private LogProducer logProducer;

    @Test
    void testKafkaIntegration() throws InterruptedException {
        // 创建测试消息
        OperationLogMessage message = new OperationLogMessage();
        message.setOperation("集成测试");
        message.setMethod("testMethod");
        message.setUsername("testUser");
        message.setTime(100L);
        message.setCreateTime(LocalDateTime.now());
        
        log.info("准备发送消息: {}", message);
        
        // 发送消息
        logProducer.sendOperationLog(message);
        
        // 等待消息被消费
        TimeUnit.SECONDS.sleep(2);
        
        // 这里可以添加断言来验证消息是否被正确处理
        // 比如检查数据库记录或其他状态
        
        log.info("测试完成");
    }
} 