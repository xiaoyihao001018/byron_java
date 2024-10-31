package org.example.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.result.R;
import org.example.dto.request.UserCreateRequest;
import org.example.security.dto.request.LoginRequest;
import org.example.security.dto.response.LoginResponse;
import org.example.security.dto.request.RegisterRequest;
import org.example.security.service.AuthService;
import org.example.service.impl.UserServiceFacade;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserServiceFacade userServiceFacade;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R<Void> register(@Valid @RequestBody RegisterRequest request) {
        userServiceFacade.createUser(new UserCreateRequest(request.getUsername(), request.getPassword()));
        return R.success();
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
} 