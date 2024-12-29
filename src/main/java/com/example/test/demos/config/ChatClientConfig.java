package com.example.test.demos.config;

import com.example.test.demos.enums.Prompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(Prompt.CHATGPT_THINKING_PROTOCOL.getValue())
                // 添加其他必要的默认配置
                .build();
    }
}
