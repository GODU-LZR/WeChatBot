package com.example.test.demos.dao;

import com.example.test.demos.pojo.ContextPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContextPromptRepository extends JpaRepository<ContextPrompt, Long> {
    Optional<ContextPrompt> findByUserNickname(String userNickname);
}
