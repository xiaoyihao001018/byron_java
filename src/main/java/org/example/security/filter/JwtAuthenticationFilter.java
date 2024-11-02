package org.example.security.filter;

import java.io.IOException;

import org.example.security.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT认证过滤器
 * 用于处理JWT token的验证和用户认证
 */
@Slf4j  // Lombok注解，自动创建日志对象
@Component  // Spring组件注解
@RequiredArgsConstructor  // Lombok注解，生成带有final字段的构造函数
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT工具类，用于token的验证和解析
    private final JwtUtil jwtUtil;
    // 用户详情服务，用于加载用户信息
    private final UserDetailsService userDetailsService;

    /**
     * 过滤器核心处理方法
     * 处理每个HTTP请求，验证JWT token并设置认证信息
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        try {
            // 从请求中获取JWT token
            String token = getJwtFromRequest(request);
            
            // 验证token是否有效
            if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                // 从token中获取用户名
                String username = jwtUtil.getUsernameFromToken(token);
                // 加载用户详情
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // 创建认证令牌
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                // 设置认证详情
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 设置认证信息到安全上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("无法设置用户认证", e);
        }
        
        // 继续过滤器链的处理
        filterChain.doFilter(request, response);
    }

    /**
     * 从HTTP请求中提取JWT token
     * 解析Authorization头部，获取Bearer token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // 获取Authorization头部值
        String bearerToken = request.getHeader("Authorization");
        // 检查并提取token
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // 去除"Bearer "前缀
        }
        return null;
    }
} 