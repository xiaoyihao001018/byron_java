package org.example.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.example.common.exception.BusinessException;
import org.example.common.util.RedisUtil;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.service.SysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;

/**
 * 用户服务门面类
 * 整合用户相关的业务逻辑，提供缓存支持
 */
@Service  // Spring服务组件注解
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class UserServiceFacade {
    
    private final SysUserService userService;         // 用户服务
    private final PasswordEncoder passwordEncoder;    // 密码编码器
    private final RedisUtil redisUtil;               // Redis工具类
    private static final String USER_CACHE_KEY = "user:detail:";  // 用户缓存key前缀
    
    /**
     * 创建新用户
     * 对密码进行加密处理
     */
    public void createUser(UserCreateRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // 密码加密
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
    }
    
    /**
     * 获取用户详情
     * 使用Spring Cache注解实现缓存
     */
    @Cacheable(value = "user", key = "#id")  // 缓存用户信息
    public UserDetailResponse getUserDetail(Long id) {
        return userService.getUserDetail(id);
    }
    
    /**
     * 更新用户信息
     * 清除相关缓存
     */
    @CacheEvict(value = "user", key = "#id")  // 更新时清除缓存
    public void updateUser(Long id, UserUpdateRequest request) {
        // 检查用户是否存在
        SysUser user = userService.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 更新用户信息
        user.setUsername(request.getUsername());
        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        userService.updateById(user);
        
        // 删除Redis缓存
        redisUtil.delete(USER_CACHE_KEY + id);
    }
    
    /**
     * 批量删除用户
     * 删除所有相关缓存
     */
    @CacheEvict(value = "user", allEntries = true, beforeInvocation = true)  // 删除所有缓存
    public void deleteUsers(List<Long> ids) {
        userService.removeByIds(ids);
    }
    
    /**
     * 分页查询用户列表
     */
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username) {
        return userService.pageUsers(page, username, null);
    }
} 