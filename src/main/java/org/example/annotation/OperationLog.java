package org.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target 指定注解可以用在什么地方，这里是METHOD表示只能用在方法上
@Target(ElementType.METHOD)         

// @Retention 指定注解的生命周期
// RUNTIME表示这个注解在运行时依然存在，可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME) 

// @Documented 表示这个注解应该被包含在JavaDoc文档中
@Documented                         

// 定义一个名为OperationLog的注解
public @interface OperationLog {
    // 定义一个value属性，默认值为空字符串
    // 使用时可以这样用: @OperationLog("创建用户")
    String value() default "";      
}