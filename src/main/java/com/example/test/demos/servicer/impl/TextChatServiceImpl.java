package com.example.test.demos.servicer.impl;

import com.example.test.demos.servicer.ChatHistoryService;
import com.example.test.demos.servicer.TextChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextChatServiceImpl implements TextChatService {
    @Autowired
    private ChatHistoryService chatHistoryService;
    @Autowired
    private ChatClient chatClient;

    public String generateTextAIResponse(String userNickname, String userMessage) {
        // 保存用户消息
        chatHistoryService.saveMessage(userNickname, userMessage, "user");
        
        // 获取历史消息
        List<Message> history = chatHistoryService.getHistoryMessages(userNickname);
        
        // 调用AI获取响应
        String response = chatClient.prompt()
            .messages(history)
            .user(userMessage)
            .call()
            .content();
            
        // 保存AI响应
        chatHistoryService.saveMessage(userNickname, response, "assistant");
        
        return response;
    }
}
