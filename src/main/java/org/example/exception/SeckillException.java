package org.example.exception;

/**
 * 秒杀业务异常类
 * 用于处理秒杀过程中的特定业务异常情况
 * 
 * 可能的异常场景包括：
 * - 商品库存不足
 * - 用户重复秒杀
 * - 秒杀活动未开始或已结束
 * - 系统限流

 */
public class SeckillException extends RuntimeException {
    
    /**
     * 构造函数
     * 
     * @param message 异常信息
     */
    public SeckillException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * 
     * @param message 异常信息
     * @param cause 原始异常
     */
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
} 