package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    
    SysUser findByUsername(String username);
    
    void createUser(UserCreateRequest request);
    
    void updateUser(Long id, UserUpdateRequest request);
    
    UserDetailResponse getUserDetail(Long id);
    
    IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status);
    
    void updateUserStatus(Long userId, Integer status);
    
    void assignDefaultRole(Long userId);
} 