package org.example.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "更新用户请求")
public class UserUpdateRequest {
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    @Schema(description = "新密码，不修改则不传")
    private String newPassword;
    
    @Schema(description = "角色ID列表")
    private List<Long> roleIds;
} 