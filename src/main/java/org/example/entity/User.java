package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("user")
@Schema(description = "用户实体")
public class User {
    @Schema(description = "用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户年龄")
    private Integer age;

    @Schema(description = "用户邮箱")
    private String email;
} 