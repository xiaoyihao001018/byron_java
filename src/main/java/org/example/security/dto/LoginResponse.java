package org.example.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "登录响应")
public class LoginResponse {
    
    @Schema(description = "访问令牌")
    private String token;
    
    @Schema(description = "令牌类型")
    private String tokenType;
    
    @Schema(description = "过期时间（秒）")
    private Long expiresIn;
} 