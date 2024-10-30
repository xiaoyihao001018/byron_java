package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.common.result.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "测试接口", description = "用于测试权限控制")
public class TestController {
    
    @GetMapping("/admin")
    @Operation(summary = "管理员测试接口")
    @PreAuthorize("hasRole('ADMIN')")
    public R<String> adminTest() {
        return R.success("管理员接口测试成功");
    }
    
    @GetMapping("/user")
    @Operation(summary = "用户测试接口")
    @PreAuthorize("hasRole('USER')")
    public R<String> userTest() {
        return R.success("用户接口测试成功");
    }
    
    @GetMapping("/public")
    @Operation(summary = "公开测试接口")
    public R<String> publicTest() {
        return R.success("公开接口测试成功");
    }
} 