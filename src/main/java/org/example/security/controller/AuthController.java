package org.example.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.result.R;
import org.example.security.dto.LoginRequest;
import org.example.security.dto.RegisterRequest;
import org.example.security.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public R<String> login(@Valid @RequestBody LoginRequest request) {
        log.debug("登录请求: username={}", request.getUsername());
        String token = authService.login(request.getUsername(), request.getPassword());
        return R.success(token);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public R<Void> register(@Valid @RequestBody RegisterRequest request) {
        log.debug("注册请求: username={}", request.getUsername());
        authService.register(request);
        return R.success();
    }
} 