package org.example.security.service;

import org.example.entity.SysUser;
import org.example.service.SysUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * UserDetailsService接口的实现类
 * 用于Spring Security加载用户特定数据的核心接口
 * 它在整个认证过程中被用来获取用户信息
 */
@Service  // Spring服务组件注解
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class UserDetailsServiceImpl implements UserDetailsService {

    // 注入用户服务
    private final SysUserService userService;

    /**
     * 根据用户名加载用户信息
     * 这个方法会在用户登录时被Spring Security调用
     *
     * @param username 用户名
     * @return UserDetails 用户详情对象
     * @throws UsernameNotFoundException 当用户不存在时抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询用户信息
        SysUser user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 构建并返回Spring Security的UserDetails对象
        return User.builder()
            .username(user.getUsername())    // 设置用户名
            .password(user.getPassword())    // 设置加密后的密码
            .authorities("USER")             // 设置用户权限
            .build();
    }
} 