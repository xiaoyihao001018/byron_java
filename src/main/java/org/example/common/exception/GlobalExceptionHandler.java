package org.example.common.exception;

import org.example.common.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // Lombok注解，自动创建日志对象
@RestControllerAdvice  // 标记这是一个全局异常处理器，用于处理Controller层的异常
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return R.fail(e.getMessage());
    }

    // 处理登录认证失败异常
    @ExceptionHandler(BadCredentialsException.class)
    public R<Void> handleBadCredentialsException(BadCredentialsException e) {
        log.error("认证失败", e);
        return R.fail("用户名或密码错误");
    }

    // 处理权限不足异常
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)  // 设置HTTP状态码为403
    public R<Void> handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足", e);
        return R.fail(HttpStatus.FORBIDDEN.value(), "权限不足");
    }

    // 处理参数绑定异常（比如表单验证失败）
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException e) {
        log.error("参数校验失败", e);
        return R.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // 处理所有未被特定处理的异常
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return R.fail("系统异常，请稍后重试");
    }
}