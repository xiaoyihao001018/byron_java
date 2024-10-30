package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.result.R;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.UserDetailResponse;
import org.example.entity.SysUser;
import org.example.service.impl.UserServiceFacade;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SysUserController {

    private final UserServiceFacade userService;

    @Operation(summary = "分页查询用户")
    @GetMapping
    public R<IPage<UserDetailResponse>> pageUsers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "用户名") @RequestParam(required = false) String username) {
        Page<SysUser> page = new Page<>(current, size);
        return R.success(userService.pageUsers(page, username));
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public R<UserDetailResponse> getUserDetail(@Parameter(description = "用户ID") @PathVariable Long id) {
        return R.success(userService.getUserDetail(id));
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public R<Void> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
        return R.success();
    }
} 