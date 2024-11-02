package org.example.security.config;

import lombok.RequiredArgsConstructor;
import org.example.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 * Spring Security配置类
 * 负责配置系统的安全认证和授权规则
 */
@Configuration  // Spring配置类注解
@EnableWebSecurity  // 启用Spring Security的Web安全支持
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class SecurityConfig {

    // 注入JWT认证过滤器
    private final JwtAuthenticationFilter jwtAuthFilter;
    // 注入用户详情服务
    private final UserDetailsService userDetailsService;

    /**
     * 配置密码编码器
     * BCryptPasswordEncoder是Spring Security提供的密码编码器，用于密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全过滤链
     * 定义系统的安全规则、过滤器链和访问权限
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // 禁用CSRF保护
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 使用无状态会话
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // 添加JWT过滤器
            .authorizeHttpRequests()  // 配置请求授权
                .requestMatchers("/api/auth/**").permitAll()  // 允许认证相关接口匿名访问
                .requestMatchers("/swagger-ui.html").permitAll()  // 允许Swagger UI访问
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()  // 其他请求需要认证
            .and()
            .authenticationManager(authenticationManager());  // 配置认证管理器

        return http.build();
    }

    /**
     * 配置认证管理器
     * 用于处理认证请求
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);  // 设置用户详情服务
        provider.setPasswordEncoder(passwordEncoder());      // 设置密码编码器
        return new ProviderManager(provider);
    }
} 