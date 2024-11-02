package org.example.common.exception;

import lombok.Getter;

@Getter  // Lombok注解，自动生成getter方法
public class BusinessException extends RuntimeException {  // 继承运行时异常
    
    // 错误码字段，final确保不可变
    private final Integer code;

    // 构造函数1：只传入错误信息，默认错误码500
    public BusinessException(String message) {
        super(message);  // 调用父类构造函数设置错误信息
        this.code = 500; // 默认错误码
    }

    // 构造函数2：传入错误信息和原始异常
    public BusinessException(String message, Throwable cause) {
        super(message, cause);  // 调用父类构造函数设置错误信息和原始异常
        this.code = 500;        // 默认错误码
    }

    // 构造函数3：传入自定义错误码和错误信息
    public BusinessException(Integer code, String message) {
        super(message);    // 调用父类构造函数设置错误信息
        this.code = code;  // 设置自定义错误码
    }

    // 构造函数4：传入所有参数
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);  // 调用父类构造函数设置错误信息和原始异常
        this.code = code;       // 设置自定义错误码
    }
}