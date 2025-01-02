package com.example.test.demos.servicer.impl;

import com.example.test.demos.dao.ChatHistoryRepository;
import com.example.test.demos.pojo.ChatHistory;
import com.example.test.demos.servicer.ChatHistoryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatHistoryServiceImpl implements ChatHistoryService {
    @Autowired
    private  ChatHistoryRepository chatHistoryRepository;

    @Transactional
    public void saveMessage(String userNickname, String content, String role) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setUserNickname(userNickname);
        chatHistory.setContent(content);
        chatHistory.setRole(role);
        chatHistory.setCreateTime(LocalDateTime.now());
        
        chatHistoryRepository.save(chatHistory);
        
        // 检查并清理超过10条的历史记录
        cleanupExcessHistory(userNickname);
    }

    @Transactional
    public List<Message> getHistoryMessages(String userNickname) {
        List<ChatHistory> histories = chatHistoryRepository
            .findByUserNicknameOrderByCreateTimeDesc(userNickname);
            
        return histories.stream()
            .map(this::convertToMessage)
            .collect(Collectors.toList());
    }

    @Transactional
    public void cleanupExcessHistory(String userNickname) {
        List<ChatHistory> histories = chatHistoryRepository
            .findByUserNicknameOrderByCreateTimeDesc(userNickname);
            
        if (histories.size() > 10) {
            List<ChatHistory> toDelete = histories.subList(10, histories.size());
            chatHistoryRepository.deleteAll(toDelete);
        }
    }

    @Transactional
    public void deleteUserHistory(String userNickname) {
        chatHistoryRepository.deleteByUserNickname(userNickname);
    }

    @Scheduled(cron = "0 0 12 * * ?")  // 每天12点执行
    @Transactional
    public void cleanAllHistory() {
        log.info("Executing daily history cleanup at {}", LocalDateTime.now());
        chatHistoryRepository.deleteAllHistory();
    }

    private Message convertToMessage(ChatHistory history) {
        return switch (history.getRole()) {
            case "system" -> new SystemMessage(history.getContent());
            case "user" -> new UserMessage(history.getContent());
            case "assistant" -> new AssistantMessage(history.getContent());
            default -> throw new IllegalArgumentException("Unknown role: " + history.getRole());
        };
    }
}
