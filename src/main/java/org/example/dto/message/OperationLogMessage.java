package org.example.dto.message;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OperationLogMessage {
    private String username;
    private String operation;
    private String method;
    private Long time;
    private LocalDateTime createTime;
} 