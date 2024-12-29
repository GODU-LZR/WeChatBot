package com.example.test.demos.servicer;

import com.example.test.demos.pojo.ContextPrompt;

import java.util.Optional;

public interface ContextPromptService {
    /**
     * 添加消息到用户的对话历史。
     *
     * @param userNickname 用户的昵称
     * @param message      消息内容
     * @param sender       消息发送者（例如："用户" 或 "AI"）
     * @return 更新后的 ContextPrompt 实体
     */
    ContextPrompt addMessage(String userNickname, String message, String sender);

    /**
     * 根据用户昵称获取 ContextPrompt。
     *
     * @param userNickname 用户的昵称
     * @return Optional 包含 ContextPrompt 或空
     */
    Optional<ContextPrompt> getContextPrompt(String userNickname);
}
