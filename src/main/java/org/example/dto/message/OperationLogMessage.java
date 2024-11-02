package org.example.dto.message;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 操作日志消息实体
 * 用于记录系统操作日志的消息对象
 */
@Data
public class OperationLogMessage {
    private String username;      // 操作用户名
    private String operation;     // 操作描述
    private String method;        // 操作方法
    private Long time;           // 操作耗时（毫秒）
    private LocalDateTime createTime;  // 操作创建时间
} 