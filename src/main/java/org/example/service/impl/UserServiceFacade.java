package org.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.service.SysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceFacade {
    
    private final SysUserService userService;
    private final PasswordEncoder passwordEncoder;
    
    public void createUser(UserCreateRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
    }
    
    public UserDetailResponse getUserDetail(Long id) {
        return userService.getUserDetail(id);
    }
    
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username) {
        return userService.pageUsers(page, username, null);
    }
} 