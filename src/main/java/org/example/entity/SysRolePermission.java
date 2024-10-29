package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("sys_role_permission")
@Schema(description = "角色权限关联")
public class SysRolePermission {
    @Schema(description = "角色ID")
    private Long roleId;
    
    @Schema(description = "权限ID")
    private Long permissionId;
} 