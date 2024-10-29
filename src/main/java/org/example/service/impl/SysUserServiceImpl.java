package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.common.exception.BusinessException;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.entity.SysRole;
import org.example.entity.SysUserRole;
import org.example.mapper.SysUserMapper;
import org.example.mapper.SysRoleMapper;
import org.example.mapper.SysUserRoleMapper;
import org.example.service.SysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    
    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }
    
    @Override
    public List<String> findUserRoles(Long userId) {
        return sysUserMapper.findUserRoles(userId);
    }
    
    @Override
    @Transactional
    public void createUser(UserCreateRequest request) {
        // 检查用户名是否已存在
        if (findByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 创建用户
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setStatus(1);
        save(user);
        
        // 分配角色
        saveUserRoles(user.getId(), request.getRoleIds());
    }
    
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateRequest request) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 更新基本信息
        if (StringUtils.hasText(request.getNickname())) {
            user.setNickname(request.getNickname());
        }
        
        // 更新密码
        if (StringUtils.hasText(request.getNewPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        
        updateById(user);
        
        // 更新角色
        if (request.getRoleIds() != null) {
            // 先删除原有角色
            sysUserMapper.deleteUserRoles(id);
            // 保存新角色
            saveUserRoles(id, request.getRoleIds());
        }
    }
    
    @Override
    public UserDetailResponse getUserDetail(Long id) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        List<SysRole> roles = sysUserMapper.findUserRoleList(id);
        
        UserDetailResponse response = new UserDetailResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setStatus(user.getStatus());
        response.setCreateTime(user.getCreateTime());
        
        List<UserDetailResponse.RoleInfo> roleInfos = roles.stream()
            .map(role -> {
                UserDetailResponse.RoleInfo roleInfo = new UserDetailResponse.RoleInfo();
                roleInfo.setRoleId(role.getId());
                roleInfo.setRoleName(role.getRoleName());
                return roleInfo;
            })
            .collect(Collectors.toList());
        response.setRoles(roleInfos);
        
        return response;
    }
    
    @Override
    public IPage<UserDetailResponse> pageUsers(Page<SysUser> page, String username, Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(username), SysUser::getUsername, username)
               .eq(status != null, SysUser::getStatus, status)
               .orderByDesc(SysUser::getCreateTime);
               
        return page(page, wrapper).convert(user -> {
            UserDetailResponse response = new UserDetailResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setNickname(user.getNickname());
            response.setStatus(user.getStatus());
            response.setCreateTime(user.getCreateTime());
            
            List<SysRole> roles = sysUserMapper.findUserRoleList(user.getId());
            List<UserDetailResponse.RoleInfo> roleInfos = roles.stream()
                .map(role -> {
                    UserDetailResponse.RoleInfo roleInfo = new UserDetailResponse.RoleInfo();
                    roleInfo.setRoleId(role.getId());
                    roleInfo.setRoleName(role.getRoleName());
                    return roleInfo;
                })
                .collect(Collectors.toList());
            response.setRoles(roleInfos);
            
            return response;
        });
    }
    
    @Override
    @Transactional
    public void updateUserStatus(Long userId, Integer status) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setStatus(status);
        updateById(user);
    }
    
    /**
     * 保存用户角色关联
     */
    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            List<SysUserRole> userRoles = roleIds.stream()
                    .map(roleId -> {
                        SysUserRole userRole = new SysUserRole();
                        userRole.setUserId(userId);
                        userRole.setRoleId(roleId);
                        return userRole;
                    })
                    .collect(Collectors.toList());
            sysUserMapper.batchInsertUserRoles(userRoles);
        }
    }
    
    @Override
    @Transactional
    public void assignDefaultRole(Long userId) {
        // 先查询默认角色是否存在
        SysRole defaultRole = sysRoleMapper.selectOne(
            new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleCode, "USER")
        );
        
        if (defaultRole != null) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(defaultRole.getId());
            sysUserRoleMapper.insert(userRole);
        } else {
            log.warn("默认用户角色不存在，请检查角色表初始化数据");
        }
    }
} 