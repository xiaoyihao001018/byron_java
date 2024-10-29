package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.UserService;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户信息")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "创建用户", description = "创建新用户")
    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }
} 