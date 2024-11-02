package org.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码生成工具类
 * 用于生成BCrypt加密的密码，主要用于测试和初始化数据
 */
public class PasswordGenerator {
    
    /**
     * 主方法，用于生成加密密码
     * 使用BCrypt算法对密码进行加密
     * 每次运行都会生成不同的加密结果（因为BCrypt会自动加入随机盐值）
     */
    public static void main(String[] args) {
        // 创建BCrypt密码编码器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 设置原始密码
        String rawPassword = "123456";
        
        // 对密码进行加密
        String encodedPassword = encoder.encode(rawPassword);
        
        // 输出原始密码和加密后的密码
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密密码: " + encodedPassword);
        
        // 可以添加验证代码
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("密码验证: " + matches);  // 应该输出 true
    }
} 