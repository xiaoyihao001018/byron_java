package org.example.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码编码器测试类
 * 用于测试BCrypt密码加密和验证功能
 */
public class PasswordEncoderTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 测试密码加密
     * 验证相同密码每次加密结果都不同
     */
    @Test
    void testPasswordEncoding() {
        String rawPassword = "123456";
        
        // 对同一密码进行多次加密
        String encoded1 = encoder.encode(rawPassword);
        String encoded2 = encoder.encode(rawPassword);
        
        // 验证每次加密结果都不同
        assertNotEquals(encoded1, encoded2, "相同密码的加密结果应该不同");
        
        // 验证所有加密结果都能通过验证
        assertTrue(encoder.matches(rawPassword, encoded1), "密码验证应该通过");
        assertTrue(encoder.matches(rawPassword, encoded2), "密码验证应该通过");
    }
    
    /**
     * 测试密码验证
     * 验证正确和错误的密码
     */
    @Test
    void testPasswordMatching() {
        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);
        
        // 测试正确密码
        assertTrue(encoder.matches(rawPassword, encodedPassword), 
            "正确密码应该验证通过");
        
        // 测试错误密码
        assertFalse(encoder.matches("wrong-password", encodedPassword), 
            "错误密码不应该通过验证");
    }
    
    /**
     * 测试密码强度
     * 验证不同强度参数的加密结果
     */
    @Test
    void testPasswordStrength() {
        String rawPassword = "123456";
        
        // 创建不同强度的编码器
        BCryptPasswordEncoder weakEncoder = new BCryptPasswordEncoder(4);  // 较弱的强度
        BCryptPasswordEncoder strongEncoder = new BCryptPasswordEncoder(12);  // 较强的强度
        
        long startTime = System.currentTimeMillis();
        String weakHash = weakEncoder.encode(rawPassword);
        long weakTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String strongHash = strongEncoder.encode(rawPassword);
        long strongTime = System.currentTimeMillis() - startTime;
        
        // 验证强度较高的加密需要更多时间
        assertTrue(strongTime > weakTime, 
            "强度较高的加密应该需要更多时间");
        
        // 验证两种强度的加密结果都能正确验证
        assertTrue(weakEncoder.matches(rawPassword, weakHash));
        assertTrue(strongEncoder.matches(rawPassword, strongHash));
    }
    
    /**
     * 测试空值和特殊字符
     */
    @Test
    void testSpecialCases() {
        // 测试空字符串
        assertDoesNotThrow(() -> encoder.encode(""), 
            "空字符串应该能够被加密");
        
        // 测试特殊字符
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        String encoded = encoder.encode(specialChars);
        assertTrue(encoder.matches(specialChars, encoded), 
            "特殊字符应该能够正确加密和验证");
    }
} 