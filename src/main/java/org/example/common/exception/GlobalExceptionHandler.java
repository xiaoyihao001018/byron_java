package org.example.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.common.result.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验异常：{}", message, e);
        return R.failed(message);
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return R.failed("系统异常，请联系管理员");
    }
} 