package com.example.test.demos.servicer.impl;

import com.example.test.demos.pojo.ContextPrompt;
import com.example.test.demos.servicer.ContextPromptService;
import com.example.test.demos.servicer.TextAiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

// AIService.java
@Service
@RequiredArgsConstructor
public class TextAiServiceImpl implements TextAiService {
    private static final Logger logger = LoggerFactory.getLogger(TextAiServiceImpl.class);

    private final ChatClient chatClient;
    private final ContextPromptService contextPromptService;
    
    public String generateAIResponse(String userNickname, String messageContent) {
        // 获取当前的历史对话
        Optional<ContextPrompt> contextOpt = contextPromptService.getContextPrompt(userNickname);
        StringBuilder promptBuilder = new StringBuilder();
        
        // 将接收到的消息添加到历史记录
        contextPromptService.addMessage(userNickname, messageContent, "用户");
        
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
        String aiResponse = this.chatClient.prompt()
                .user(promptBuilder.toString() + messageContent + "AI:")
                .call()
                .content();
                
        // 将 AI 的回复添加到历史记录
        contextPromptService.addMessage(userNickname, aiResponse, "AI");
        
        return aiResponse;
    }
}
