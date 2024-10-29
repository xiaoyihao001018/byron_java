package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("sys_user_role")
@Schema(description = "用户角色关联")
public class SysUserRole {
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "角色ID")
    private Long roleId;
} 