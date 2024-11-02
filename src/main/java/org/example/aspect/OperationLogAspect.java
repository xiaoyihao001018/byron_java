package org.example.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.dto.message.OperationLogMessage;
import org.example.mq.producer.LogProducer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect     // 声明这是一个切面类，用于实现AOP功能
@Component  // 将这个类交给Spring容器管理
@RequiredArgsConstructor  // Lombok注解，自动生成带有final字段的构造函数
public class OperationLogAspect {

    // 注入日志消息生产者
    private final LogProducer logProducer;

    // @Around注解表示这是一个环绕通知，可以在目标方法执行前后都进行处理
    // 这里会拦截所有带有@OperationLog注解的方法
    @Around("@annotation(org.example.annotation.OperationLog)") 
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 1. 记录方法执行开始时间
        long startTime = System.currentTimeMillis();
        
        // 2. 执行原始方法，point.proceed()会调用实际的业务方法
        Object result = point.proceed();
        
        // 3. 记录方法执行结束时间
        long endTime = System.currentTimeMillis();

        // 4. 构建日志消息对象
        OperationLogMessage message = new OperationLogMessage();
        message.setMethod(point.getSignature().getName()); // 设置方法名
        message.setTime(endTime - startTime);             // 设置执行耗时
        message.setCreateTime(LocalDateTime.now());       // 设置创建时间
        
        // 5. 发送日志消息到消息队列
        logProducer.sendOperationLog(message);
        
        // 6. 返回原始方法的执行结果
        return result;
    }
} 