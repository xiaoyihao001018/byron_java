package org.example.security.controller;

import org.example.security.dto.request.LoginRequest;
import org.example.security.dto.response.LoginResponse;
import org.example.security.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试控制器测试类
 * 用于测试需要认证的API接口
 */
@SpringBootTest                // 启动完整的Spring应用上下文
@AutoConfigureMockMvc         // 自动配置MockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;   // 注入MockMvc，用于模拟HTTP请求
    
    @Autowired
    private AuthService authService;  // 注入认证服务
    
    private String adminToken;  // 存储管理员token
    
    /**
     * 测试前的准备工作
     * 获取管理员token用于后续测试
     */
    @BeforeEach
    void setUp() {
        // 获取管理员token
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        LoginResponse response = authService.login(request).getData();
        adminToken = response.getToken();
    }
    
    /**
     * 测试管理员访问权限
     * 验证带有有效token的请求能否访问管理员接口
     */
    @Test
    void testAdminAccess() throws Exception {
        mockMvc.perform(get("/api/test/admin")
                .header("Authorization", "Bearer " + adminToken))  // 添加token到请求头
            .andExpect(status().isOk())                          // 验证响应状态为200
            .andExpect(jsonPath("$.code").value(200))            // 验证响应码
            .andDo(print());                                     // 打印请求和响应详情
    }
    
    /**
     * 测试未授权访问
     * 验证没有token的请求是否会被拒绝
     */
    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/test/admin"))                  // 不带token的请求
            .andExpect(status().isUnauthorized())                // 验证响应状态为401
            .andDo(print());                                     // 打印请求和响应详情
    }
} 