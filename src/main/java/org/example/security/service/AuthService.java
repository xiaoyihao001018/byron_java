package org.example.security.service;

import java.time.LocalDateTime;

import org.example.common.exception.BusinessException;
import org.example.common.result.R;
import org.example.entity.SysUser;
import org.example.security.dto.request.LoginRequest;
import org.example.security.dto.request.RegisterRequest;
import org.example.security.dto.response.LoginResponse;
import org.example.security.util.JwtUtil;
import org.example.service.SysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证服务类
 * 处理用户登录和注册相关的业务逻辑
 */
@Service  // Spring服务组件注解
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
@Slf4j  // Lombok注解，自动创建日志对象
public class AuthService {

    // 注入所需的依赖
    private final AuthenticationManager authenticationManager;  // 认证管理器
    private final JwtUtil jwtUtil;                            // JWT工具类
    private final PasswordEncoder passwordEncoder;             // 密码编码器
    private final SysUserService userService;                 // 用户服务

    /**
     * 处理用户登录请求
     * 验证用户凭证并生成JWT令牌
     *
     * @param request 登录请求，包含用户名和密码
     * @return 包含JWT令牌的响应对象
     */
    public R<LoginResponse> login(LoginRequest request) {
        try {
            // 1. 创建认证令牌
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );
            
            // 2. 认证成功，生成JWT令牌
            String token = jwtUtil.generateToken(authentication);
            return R.success(new LoginResponse(token));
            
        } catch (AuthenticationException e) {
            // 3. 认证失败，记录日志并返回错误信息
            log.error("认证失败", e);
            return R.fail("用户名或密码错误");
        }
    }

    /**
     * 处理用户注册请求
     * 创建新用户账号
     *
     * @param request 注册请求，包含用户名和密码
     * @throws BusinessException 当用户名已存在时抛出异常
     */
    public void register(RegisterRequest request) {
        // 1. 检查用户名是否已存在
        if (userService.findByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        // 2. 创建新用户
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        // 对密码进行加密
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreateTime(LocalDateTime.now());

        // 3. 保存用户
        userService.save(user);
    }
}