package com.example.test.demos.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import org.stringtemplate.v4.ST;

/**
 * 用于接收微信消息的请求数据。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequestDTO {

    /**
     * 功能类型，例如 text, file, urlLink 等。
     */
    private String type;

    /**
     * 传输的内容，文本消息时为字符串。
     */
    private String content;

    /**
     * 消息的相关发送方数据
     */
    private String source;

    /**
     * 该消息是否 @ 您，"1" 或 "0"。
     */
    private String isMentioned;

    /**
     * 该消息是否来自您自己，"1" 或 "0"。
     */
    private String isMsgFromSelf;
}
