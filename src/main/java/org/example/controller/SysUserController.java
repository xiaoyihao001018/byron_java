package org.example.controller;

import org.example.annotation.OperationLog;
import org.example.common.result.R;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.service.impl.UserServiceFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 系统用户控制器
 * 处理用户相关的HTTP请求
 */
@Tag(name = "用户管理", description = "用户管理相关接口")  // Swagger文档分组标签
@RestController                                         // RESTful API控制器
@RequestMapping("/api/users")                          // 统一路径前缀
@RequiredArgsConstructor                               // Lombok注解，生成带有final字段的构造函数
@SecurityRequirement(name = "Bearer Authentication")    // Swagger文档安全认证配置
public class SysUserController {

    // 注入用户服务门面类
    private final UserServiceFacade userService;

    /**
     * 分页查询用户列表
     * GET /api/users?current=1&size=10&username=xxx
     */
    @Operation(summary = "分页查询用户")
    @GetMapping
    public R<IPage<UserDetailResponse>> pageUsers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "用户名") @RequestParam(required = false) String username) {
        Page<SysUser> page = new Page<>(current, size);
        return R.success(userService.pageUsers(page, username));
    }

    /**
     * 获取用户详情
     * GET /api/users/{id}
     */
    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public R<UserDetailResponse> getUserDetail(@Parameter(description = "用户ID") @PathVariable Long id) {
        return R.success(userService.getUserDetail(id));
    }

    /**
     * 创建新用户
     * POST /api/users
     */
    @OperationLog("创建用户")  // 自定义注解，用于操作日志记录
    @PostMapping
    public R<Void> createUser(@Valid @RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return R.success();
    }
} 