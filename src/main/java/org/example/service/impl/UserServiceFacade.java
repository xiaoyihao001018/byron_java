package org.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.service.SysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.example.common.util.RedisUtil;
import org.example.common.exception.BusinessException;
import java.util.List;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceFacade {
    
    private final SysUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private static final String USER_CACHE_KEY = "user:detail:";
    
    public void createUser(UserCreateRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
    }
    
    @Cacheable(value = "user", key = "#id")
    public UserDetailResponse getUserDetail(Long id) {
        return userService.getUserDetail(id);
    }
    
    @CacheEvict(value = "user", key = "#id")
    public void updateUser(Long id, UserUpdateRequest request) {
        SysUser user = userService.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setUsername(request.getUsername());
        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        userService.updateById(user);
        
        redisUtil.delete(USER_CACHE_KEY + id);
    }
    
    @CacheEvict(value = "user", allEntries = true, beforeInvocation = true)
    public void deleteUsers(List<Long> ids) {
        userService.removeByIds(ids);
    }
    
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username) {
        return userService.pageUsers(page, username, null);
    }
} 