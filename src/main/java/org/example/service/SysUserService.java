package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    
    SysUser findByUsername(String username);
    
    IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status);
    
    UserDetailResponse getUserDetail(Long userId);
} 