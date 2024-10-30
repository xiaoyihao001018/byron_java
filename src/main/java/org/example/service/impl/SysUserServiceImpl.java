package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.exception.BusinessException;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void createUser(UserCreateRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        save(user);
    }

    @Override
    public void updateUser(Long id, UserUpdateRequest request) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setUsername(request.getUsername());
        if (StringUtils.hasText(request.getNewPassword())) {
            user.setPassword(request.getNewPassword());
        }
        
        updateById(user);
    }

    @Override
    public UserDetailResponse getUserDetail(Long id) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserDetailResponse response = new UserDetailResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setCreateTime(user.getCreateTime());
        return response;
    }

    @Override
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status) {
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
            .like(StringUtils.hasText(username), SysUser::getUsername, username)
            .orderByDesc(SysUser::getCreateTime);
            
        // 执行分页查询
        IPage<SysUser> userPage = this.page(page, wrapper);
        
        // 转换结果，只保留需要的字段
        return userPage.convert(user -> {
            UserDetailResponse response = new UserDetailResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setCreateTime(user.getCreateTime());
            return response;
        });
    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        updateById(user);
    }

    @Override
    public void assignDefaultRole(Long userId) {
        // 由于我们已经移除了角色相关的功能，这个方法可以留空
        log.info("用户 {} 分配默认角色", userId);
    }
} 