package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.exception.BusinessException;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    private final SysUserMapper userMapper;
    
    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
            .like(StringUtils.hasText(username), SysUser::getUsername, username)
            .orderByDesc(SysUser::getCreateTime);
            
        IPage<SysUser> userPage = this.page(page, wrapper);
        
        return userPage.convert(user -> {
            UserDetailResponse response = new UserDetailResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setCreateTime(user.getCreateTime());
            return response;
        });
    }
    
    @Override
    public UserDetailResponse getUserDetail(Long userId) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        UserDetailResponse response = new UserDetailResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setCreateTime(user.getCreateTime());
        return response;
    }
}