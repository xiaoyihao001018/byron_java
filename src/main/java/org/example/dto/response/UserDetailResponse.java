package org.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户详情响应DTO
 * 用于向前端返回用户详细信息的数据传输对象
 */
@Data  // Lombok注解，自动生成getter、setter、toString等方法
@Schema(description = "用户详情响应")  // Swagger注解，用于API文档生成
public class UserDetailResponse {
    
    /**
     * 用户ID
     * 系统自动生成的唯一标识符
     */
    @Schema(description = "用户ID", example = "1")
    private Long id;
    
    /**
     * 用户名
     * 用户登录系统使用的唯一用户名
     */
    @Schema(description = "用户名", example = "john_doe")
    private String username;
    
    /**
     * 创建时间
     * 用户账号的创建时间
     */
    @Schema(description = "创建时间", example = "2024-01-01 12:00:00")
    private LocalDateTime createTime;
} 