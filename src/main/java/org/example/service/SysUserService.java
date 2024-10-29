package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import java.util.List;

public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查找用户
     */
    SysUser findByUsername(String username);
    
    /**
     * 查询用户角色编码列表
     */
    List<String> findUserRoles(Long userId);
    
    /**
     * 创建用户
     */
    void createUser(UserCreateRequest request);
    
    /**
     * 更新用户
     */
    void updateUser(Long id, UserUpdateRequest request);
    
    /**
     * 获取用户详情
     */
    UserDetailResponse getUserDetail(Long id);
    
    /**
     * 分页查询用户
     */
    IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status);
    
    /**
     * 更新用户状态
     */
    void updateUserStatus(Long id, Integer status);
    
    /**
     * 分配默认角色
     */
    void assignDefaultRole(Long userId);
} 