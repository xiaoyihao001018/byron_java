package org.example.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 秒杀消息实体
 * 用于处理商品秒杀的消息对象
 */
@Data                // Lombok注解，自动生成getter、setter、toString等方法
@NoArgsConstructor   // 生成无参构造函数
@AllArgsConstructor  // 生成全参构造函数
public class SeckillMessage {
    private Long userId;     // 用户ID
    private Long productId;  // 商品ID
    private Integer quantity; // 购买数量
} 