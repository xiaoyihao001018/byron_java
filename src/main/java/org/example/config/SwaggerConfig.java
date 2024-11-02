package org.example.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Swagger/OpenAPI 配置类
 * 用于配置 API 文档的基本信息和安全认证方式
 */
@Configuration  // 标记这是一个Spring配置类
@OpenAPIDefinition(  // API 文档基本信息配置
    info = @Info(
        title = "系统管理接口文档",        // API 文档标题
        description = "提供用户、角色、权限等相关接口",  // API 文档描述
        version = "1.0"                    // API 版本号
    )
)
@SecurityScheme(  // 安全认证配置
    name = "Bearer Authentication",        // 认证方案名称
    type = SecuritySchemeType.HTTP,        // 认证类型为 HTTP
    bearerFormat = "JWT",                  // Bearer 令牌格式为 JWT
    scheme = "bearer"                      // 使用 Bearer 认证方案
)
public class SwaggerConfig {
}  