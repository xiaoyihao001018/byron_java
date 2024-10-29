package org.example.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.exception.BusinessException;
import org.example.entity.SysUser;
import org.example.security.dto.LoginRequest;
import org.example.security.dto.LoginResponse;
import org.example.security.dto.RegisterRequest;
import org.example.security.util.JwtUtil;
import org.example.service.SysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final SysUserService userService;

    public String login(String username, String password) {
        try {
            // 打印调试信息
            SysUser user = userService.findByUsername(username);
            if (user != null) {
                log.debug("数据库中的密码: {}", user.getPassword());
                log.debug("输入密码的加密结果: {}", passwordEncoder.encode(password));
                log.debug("密码匹配结果: {}", passwordEncoder.matches(password, user.getPassword()));
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            return jwtUtil.generateToken(authentication);
        } catch (AuthenticationException e) {
            log.error("认证失败: {}", e.getMessage(), e);
            throw new BusinessException("登录失败");
        }
    }

    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userService.findByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        // 创建用户
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setStatus(1);

        // 保存用户
        userService.save(user);

        // 分配默认角色
        userService.assignDefaultRole(user.getId());
    }
} 