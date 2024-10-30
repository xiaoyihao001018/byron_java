package org.example.security.controller;

import org.example.security.service.AuthService;
import org.example.security.vo.LoginRequest;
import org.example.security.vo.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AuthService authService;
    
    private String adminToken;
    
    @BeforeEach
    void setUp() {
        // 获取管理员token
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        LoginResponse response = authService.login(request).getData();
        adminToken = response.getToken();
    }
    
    @Test
    void testAdminAccess() throws Exception {
        mockMvc.perform(get("/api/test/admin")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andDo(print());
    }
    
    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/test/admin"))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }
} 