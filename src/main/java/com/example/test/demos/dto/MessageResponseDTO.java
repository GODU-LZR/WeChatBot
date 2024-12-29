package com.example.test.demos.dto;

import lombok.*;

/**
 * 用于封装整个响应结构，包括 success 和 data 字段。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponseDTO {
    
    /**
     * 该条请求成功与否，返回 false 或者无该字段，不会处理回复。
     * 有一些特殊消息也通过该字段控制，比如加好友邀请，返回 true 则会通过好友请求。
     */
    private Boolean success;
    
    /**
     * 如果需要回复消息，需要定义 data 字段。
     * 可以是单个 ReplyDataDTO 对象或 ReplyDataDTO 对象的数组。
     */
    private Object data;
}
