package com.example.test.demos.dto;

import lombok.*;

/**
 * 用于封装回复消息的具体内容。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDataDTO {
    
    /**
     * 消息类型，字段留空解析为纯文本。
     * 可选值：text, fileUrl, base64
     */
    private String type;
    
    /**
     * 消息内容，type 为 fileUrl 或 base64 时会解析 content 成文件。
     */
    private String content;
    
    /**
     * 发送的文件名别名，仅当 type 为 fileUrl 或 base64 有效。
     * 用来解决自定义发送文件名问题，base64 类型不填该参数，默认为 时间戳+文件后缀名。
     */
    private String fileAlias;
}
