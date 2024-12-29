package com.example.test.demos.servicer.impl;

import com.example.test.demos.dao.ContextPromptRepository;

import com.example.test.demos.pojo.ContextPrompt;
import com.example.test.demos.servicer.ContextPromptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class ContextPromptServiceImpl implements ContextPromptService {
    private final ContextPromptRepository contextPromptRepository;
    private static final int MAX_HISTORY = 10;
    private final Logger logger = LoggerFactory.getLogger(ContextPromptServiceImpl.class);

    @Override
    @Transactional
    public ContextPrompt addMessage(String userNickname, String message, String role) {
        try {
            ContextPrompt contextPrompt = contextPromptRepository.findByUserNickname(userNickname)
                    .orElseGet(() -> ContextPrompt.builder()
                            .userNickname(userNickname)
                            .history(new LinkedList<>())
                            .build());

            Queue<String> history = contextPrompt.getHistory();
            String formattedMessage = String.format("{\"role\": \"%s\", \"content\": \"%s\"}",
                    role, message.replace("\"", "\\\""));
            history.add(formattedMessage);

            while (history.size() > MAX_HISTORY) {
                history.poll();
            }

            contextPrompt.setHistory(history);
            return contextPromptRepository.save(contextPrompt);
        } catch (Exception e) {
            logger.error("Error adding message for user: " + userNickname, e);
            throw new RuntimeException("Failed to add message", e);
        }
    }

    @Override
    public Optional<ContextPrompt> getContextPrompt(String userNickname) {
        try {
            return contextPromptRepository.findByUserNickname(userNickname);
        } catch (Exception e) {
            logger.error("Error getting context for user: " + userNickname, e);
            throw new RuntimeException("Failed to get context", e);
        }
    }
}

