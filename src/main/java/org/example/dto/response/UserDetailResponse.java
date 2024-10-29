package org.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "用户详情响应")
public class UserDetailResponse {
    
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "状态：1-正常，0-禁用")
    private Integer status;
    
    @Schema(description = "角色列表")
    private List<RoleInfo> roles;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Data
    @Schema(description = "角色信息")
    public static class RoleInfo {
        @Schema(description = "角色ID")
        private Long roleId;
        
        @Schema(description = "角色名称")
        private String roleName;
    }
} 