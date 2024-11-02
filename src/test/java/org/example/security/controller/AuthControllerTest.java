package org.example.security.controller;

import org.example.security.model.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 认证控制器测试类
 * 测试登录等认证相关接口
 */
@SpringBootTest                // 启动完整的Spring应用上下文
@AutoConfigureMockMvc         // 自动配置MockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;   // 注入MockMvc，用于模拟HTTP请求
    
    /**
     * 测试登录接口
     * 验证成功登录场景
     */
    @Test
    void testLogin() throws Exception {
        // 创建登录请求对象
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        
        // 执行POST请求并验证响应
        mockMvc.perform(post("/api/auth/login")                           // 发送POST请求
                .contentType(MediaType.APPLICATION_JSON)                   // 设置Content-Type
                .content(new ObjectMapper().writeValueAsString(request))) // 将请求对象转换为JSON
            .andExpect(status().isOk())                                  // 验证响应状态为200
            .andExpect(jsonPath("$.code").value(200))                    // 验证响应码
            .andExpect(jsonPath("$.data.token").exists());              // 验证token存在
    }
} 