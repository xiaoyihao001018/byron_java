package org.example.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok注解，自动生成getter/setter/toString等方法
@NoArgsConstructor  // 生成无参构造函数
@AllArgsConstructor // 生成全参构造函数
public class R<T> {  // 泛型类，T表示数据的类型
    private Integer code;    // 状态码
    private String message;  // 消息
    private T data;         // 数据，使用泛型

    // 成功响应，带数据
    public static <T> R<T> success(T data) {
        return new R<>(200, "success", data);
    }

    // 成功响应，无数据
    public static <T> R<T> success() {
        return success(null);
    }

    // 失败响应，带消息
    public static <T> R<T> fail(String message) {
        return new R<>(500, message, null);
    }

    // 错误响应，带消息（同fail）
    public static <T> R<T> error(String message) {
        return new R<>(500, message, null);
    }

    // 失败响应，带状态码和消息
    public static <T> R<T> fail(Integer code, String message) {
        return new R<>(code, message, null);
    }
}