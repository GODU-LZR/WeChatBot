package com.example.test.demos.servicer;

import com.example.test.demos.pojo.ChatHistory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ChatHistoryService {
    void saveMessage(String userNickname, String content, String role);
    List<Message> getHistoryMessages(String userNickname);
    void cleanupExcessHistory(String userNickname);
    void deleteUserHistory(String userNickname);
    void cleanAllHistory();

}
