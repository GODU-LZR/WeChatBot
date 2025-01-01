package com.example.test.demos.controller;
import com.example.test.demos.dto.*;
import com.example.test.demos.pojo.ContextPrompt;
import com.example.test.demos.servicer.ContextPromptService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "AI 聊天接口", description = "与 ChatGPT 进行交互的 API")
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);

    private final ChatClient chatClient;
    private final ContextPromptService contextPromptService;
    private final Gson gson;

    /**
     * 接受微信消息并快速回复单条消息
     */
    @Operation(summary = "接受微信消息并快速回复", description = "处理来自微信的消息请求，并根据需要进行自定义快速回复。")
    @ApiResponse(responseCode = "200", description = "成功处理消息并返回回复")
    @PostMapping(value = "/receiveMessage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponseDTO receiveMessage(
            @Valid @ModelAttribute MessageRequestDTO messageRequest) {

        long startTime = System.currentTimeMillis();

        // 将source字符串转换为Source对象
        Source sourceObj;
        try {
            sourceObj = gson.fromJson(messageRequest.getSource(), Source.class);
        } catch (Exception e) {
            logger.error("Failed to parse source JSON: {}", messageRequest.getSource(), e);
            return MessageResponseDTO.builder().success(false).build();
        }

        logger.info("接收到消息: type={}, isMentioned={}, isMsgFromSelf={}",
                messageRequest.getType(),
                messageRequest.getIsMentioned(),
                messageRequest.getIsMsgFromSelf());


        String userNickname = sourceObj.getFrom().getPayload().getName();
        System.out.println(userNickname);

        if (userNickname == null || userNickname.isEmpty()) {
            logger.warn("无法解析用户昵称，source={}", messageRequest.getSource());
            return MessageResponseDTO.builder().success(false).build();
        }

        String messageContent = messageRequest.getContent();

        logger.info("处理消息内容: {}", messageContent);



        // 获取当前的历史对话
        Optional<ContextPrompt> contextOpt = contextPromptService.getContextPrompt(userNickname);
        StringBuilder promptBuilder = new StringBuilder();

        if (contextOpt.isPresent()) {
            for (String msg : contextOpt.get().getHistory()) {
                promptBuilder.append(msg).append("\n");
            }
        } else {
            // 如果没有历史记录，初始化对话
            logger.warn("用户 '{}' 没有历史记录，初始化中...", userNickname);
            contextPromptService.addMessage(userNickname, "初始化对话", "系统");
            contextOpt = contextPromptService.getContextPrompt(userNickname);
            if (contextOpt.isPresent()) {
                for (String msg : contextOpt.get().getHistory()) {
                    promptBuilder.append(msg).append("\n");
                }
            }
        }

        logger.debug("生成的提示词: {}", promptBuilder.toString());

        // 调用 ChatClient 生成回复
        String aiResponse;
        try {
            aiResponse = this.chatClient.prompt()
                    .user(promptBuilder.toString()+messageContent+"AI:")
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("调用 ChatClient 生成回复时发生错误", e);
            return MessageResponseDTO.builder().success(false).build();
        }

        logger.info("AI 回复: {}", aiResponse);

        // 将接收到的消息添加到历史记录
        contextPromptService.addMessage(userNickname, messageContent, "用户");

        // 将 AI 的回复添加到历史记录
        contextPromptService.addMessage(userNickname, aiResponse, "AI");

        // 构建回复数据
        ReplyDataDTO replyData = ReplyDataDTO.builder()
                .type("text")
                .content(aiResponse)
                .build();

        // 构建响应对象
        MessageResponseDTO response = MessageResponseDTO.builder()
                .success(true)
                .data(replyData)
                .build();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("处理消息耗时: {} ms", duration);

        return response;
    }

}
