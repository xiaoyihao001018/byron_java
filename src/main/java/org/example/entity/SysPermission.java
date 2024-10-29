package org.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_permission")
@Schema(description = "系统权限")
public class SysPermission {
    @TableId(type = IdType.AUTO)
    @Schema(description = "权限ID")
    private Long id;

    @NotBlank(message = "权限名称不能为空")
    @Schema(description = "权限名称")
    private String permissionName;

    @NotBlank(message = "权限编码不能为空")
    @Schema(description = "权限编码")
    private String permissionCode;

    @Schema(description = "权限描述")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 