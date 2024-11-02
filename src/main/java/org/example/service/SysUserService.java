package org.example.service;

import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统用户服务接口
 * 继承MyBatis-Plus的IService接口，获得基础的CRUD功能
 */
public interface SysUserService extends IService<SysUser> {
    
    /**
     * 根据用户名查询用户
     * 用于用户登录验证和用户信息获取
     *
     * @param username 用户名
     * @return 用户实体，如果未找到返回null
     */
    SysUser findByUsername(String username);
    
    /**
     * 分页查询用户列表
     * 支持按用户名模糊搜索和状态筛选
     *
     * @param page 分页参数
     * @param username 用户名（可选）
     * @param status 用户状态（可选）
     * @return 分页结果，包含用户详情DTO
     */
    IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status);
    
    /**
     * 获取用户详情
     * 根据用户ID查询详细信息
     *
     * @param userId 用户ID
     * @return 用户详情DTO
     * @throws BusinessException 当用户不存在时抛出异常
     */
    UserDetailResponse getUserDetail(Long userId);
} 