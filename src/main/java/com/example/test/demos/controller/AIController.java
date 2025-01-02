package com.example.test.demos.controller;
import com.example.test.demos.dto.*;
import com.example.test.demos.servicer.TextAiService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "AI 聊天接口", description = "与 ChatGPT 进行交互的 API")
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);

    private final TextAiService textAiService;
    private final Gson gson;

    // AIController.java 中的处理逻辑
    @PostMapping("/receiveMessage")
    public MessageResponseDTO receiveMessage(@Valid @ModelAttribute MessageRequestDTO messageRequest) {
        messageRequest.parseSource(gson);

        logger.info("接收到消息: type={}, isMentioned={}, isMsgFromSelf={}",
                messageRequest.getType(), messageRequest.getIsMentioned(), messageRequest.getIsMsgFromSelf());

        // 根据消息类型处理
        return switch (messageRequest.getMessageType()) {
            case TEXT -> handleTextMessage(messageRequest);
            case SYSTEM_EVENT_LOGIN -> handleLoginEvent(messageRequest);
            case SYSTEM_EVENT_PUSH_NOTIFY -> handlePushNotify(messageRequest);
            default -> MessageResponseDTO.builder().success(false).build();
        };
    }

    private MessageResponseDTO handleTextMessage(MessageRequestDTO messageRequest) {
        // 检查用户昵称
        String username = messageRequest.getUserNickname();
        if (username == null) {
            logger.warn("无法解析用户昵称，source={}", messageRequest.getSource());
            return MessageResponseDTO.builder().success(false).build();
        }

        // 生成AI回复
        String reply = textAiService.generateAIResponse(username, messageRequest.getContent());
        if (reply == null || reply.trim().isEmpty()) {
            logger.warn("AI回复内容为空");
            return MessageResponseDTO.builder().success(false).build();
        }

        // 构建回复数据
        ReplyDataDTO replyData = ReplyDataDTO.builder()
                .type("text")                // 设置为文本类型
                .content(reply)              // 设置AI生成的回复内容
                .build();                    // fileAlias不需要设置,因为是文本消息

        // 构建并返回响应
        return MessageResponseDTO.builder()
                .success(true)               // 设置处理成功
                .data(replyData)            // 设置回复数据
                .build();
    }



    private MessageResponseDTO handleLoginEvent(MessageRequestDTO messageRequest) {
        logger.info("处理登录事件: {}", messageRequest.getContent());
        return MessageResponseDTO.builder().success(true).build();
    }

    private MessageResponseDTO handlePushNotify(MessageRequestDTO messageRequest) {
        logger.info("处理送达通知: {}", messageRequest.getContent());
        return MessageResponseDTO.builder().success(true).build();
    }

}
