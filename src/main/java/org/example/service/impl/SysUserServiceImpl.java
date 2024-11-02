package org.example.service.impl;

import org.example.common.exception.BusinessException;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 系统用户服务实现类
 * 继承MyBatis-Plus的ServiceImpl，实现基础的CRUD功能
 */
@Service  // Spring服务组件注解
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    // 注入用户Mapper接口
    private final SysUserMapper userMapper;
    
    /**
     * 根据用户名查询用户
     * 用于用户登录和注册时的用户查询
     *
     * @param username 用户名
     * @return 用户实体，如果未找到则返回null
     */
    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    /**
     * 分页查询用户列表
     * 支持按用户名模糊搜索，并按创建时间倒序排序
     *
     * @param page 分页参数
     * @param username 用户名（可选）
     * @param status 用户状态（可选）
     * @return 分页结果，包含用户详情DTO
     */
    @Override
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status) {
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
            .like(StringUtils.hasText(username), SysUser::getUsername, username)  // 用户名模糊查询
            .orderByDesc(SysUser::getCreateTime);  // 按创建时间倒序
            
        // 执行分页查询
        IPage<SysUser> userPage = this.page(page, wrapper);
        
        // 转换为DTO对象
        return userPage.convert(user -> {
            UserDetailResponse response = new UserDetailResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setCreateTime(user.getCreateTime());
            return response;
        });
    }
    
    /**
     * 获取用户详情
     * 根据用户ID查询用户信息，并转换为DTO
     *
     * @param userId 用户ID
     * @return 用户详情DTO
     * @throws BusinessException 当用户不存在时抛出异常
     */
    @Override
    public UserDetailResponse getUserDetail(Long userId) {
        // 查询用户
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 转换为DTO
        UserDetailResponse response = new UserDetailResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setCreateTime(user.getCreateTime());
        return response;
    }
}