package org.example.security.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT工具类
 * 用于处理JWT令牌的生成、解析和验证
 */
@Slf4j  // Lombok注解，自动创建日志对象
@Component  // Spring组件注解，标记为Spring管理的Bean
public class JwtUtil {

    // 从配置文件中注入JWT密钥
    @Value("${jwt.secret}")
    private String jwtSecret;

    // 从配置文件中注入JWT过期时间（毫秒）
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * 获取用于签名的密钥
     * 使用HMAC-SHA算法创建密钥
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * 生成JWT令牌
     * 使用用户认证信息创建JWT令牌
     *
     * @param authentication Spring Security的认证对象
     * @return JWT令牌字符串
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // 设置主题（用户名）
                .setIssuedAt(now)                      // 设置签发时间
                .setExpiration(expiryDate)             // 设置过期时间
                .signWith(getSigningKey())             // 使用密钥签名
                .compact();                            // 生成JWT字符串
    }

    /**
     * 从JWT令牌中提取用户名
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 验证JWT令牌的有效性
     * 检查令牌是否被篡改、是否过期等
     *
     * @param token JWT令牌
     * @return 如果令牌有效返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("无效的JWT签名: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("无效的JWT令牌: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT令牌已过期: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("不支持的JWT令牌: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT声明为空: {}", e.getMessage());
        }
        return false;
    }
}