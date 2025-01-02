package com.example.test.demos.dto;

import com.example.test.demos.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRequestDTO {
    private String type;
    private String content;
    private String source;
    private String isMentioned;
    private String isMsgFromSelf;

    @JsonIgnore
    private Source parsedSource;

    public MessageType getMessageType() {
        return MessageType.fromString(type);
    }

    public boolean isSystemEvent() {
        return type != null && type.startsWith("system_event_");
    }

    public String getUserNickname() {
        if (parsedSource == null || parsedSource.getFrom() == null
                || parsedSource.getFrom().getPayload() == null) {
            return null;
        }
        return parsedSource.getFrom().getPayload().getName();
    }

    public void parseSource(Gson gson) {
        if (source != null) {
            this.parsedSource = gson.fromJson(source, Source.class);
        }
    }
}

