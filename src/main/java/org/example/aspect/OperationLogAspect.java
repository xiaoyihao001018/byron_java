package org.example.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.dto.message.OperationLogMessage;
import org.example.mq.producer.LogProducer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final LogProducer logProducer;

    @Around("@annotation(org.example.annotation.OperationLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();

        // 构建日志消息
        OperationLogMessage message = new OperationLogMessage();
        message.setMethod(point.getSignature().getName());
        message.setTime(endTime - startTime);
        message.setCreateTime(LocalDateTime.now());
        
        // 发送消息
        logProducer.sendOperationLog(message);
        
        return result;
    }
} 