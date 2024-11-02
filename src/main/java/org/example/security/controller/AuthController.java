package org.example.security.controller;

import org.example.common.result.R;
import org.example.dto.request.UserCreateRequest;
import org.example.security.dto.request.LoginRequest;
import org.example.security.dto.request.RegisterRequest;
import org.example.security.dto.response.LoginResponse;
import org.example.security.service.AuthService;
import org.example.service.impl.UserServiceFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "认证管理", description = "认证相关接口")  // Swagger注解，用于API文档
@RestController    // 标记这是一个REST控制器
@RequestMapping("/api/auth")  // 所有接口都以/api/auth开头
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class AuthController {

    // 注入认证服务
    private final AuthService authService;
    // 注入用户服务门面
    private final UserServiceFacade userServiceFacade;

    @Operation(summary = "用户注册")  // Swagger注解，描述接口用途
    @PostMapping("/register")   // 处理POST请求，路径为/api/auth/register
    public R<Void> register(@Valid @RequestBody RegisterRequest request) {
        userServiceFacade.createUser(
            new UserCreateRequest(request.getUsername(), request.getPassword())
        );
        return R.success();
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")    // 处理POST请求，路径为/api/auth/login
    public R<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}