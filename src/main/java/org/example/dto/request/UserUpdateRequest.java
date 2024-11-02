package org.example.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户更新请求DTO
 * 用于接收更新用户信息的请求数据
 */
@Data
@Schema(description = "用户更新请求")
public class UserUpdateRequest {
    
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @Schema(description = "密码", required = false)
    private String password;  // 更新时密码可选
} 