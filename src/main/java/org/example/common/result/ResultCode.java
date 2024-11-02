package org.example.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter  // Lombok注解，自动生成getter方法
@AllArgsConstructor  // Lombok注解，生成包含所有字段的构造函数
public enum ResultCode {
    // 定义常用的响应状态
    SUCCESS(200, "操作成功"),         // 成功
    FAILED(500, "操作失败"),          // 失败
    VALIDATE_FAILED(404, "参数检验失败"), // 参数错误
    UNAUTHORIZED(401, "暂未登录或token已经过期"), // 未认证
    FORBIDDEN(403, "没有相关权限");    // 无权限

    private final int code;     // 状态码
    private final String message;  // 状态描述
}