package com.example.test.demos.dao;

import com.example.test.demos.pojo.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByUserNicknameOrderByCreateTimeDesc(String userNickname);
    
    void deleteByUserNickname(String userNickname);
    
    @Modifying
    @Query("DELETE FROM ChatHistory")
    void deleteAllHistory();
}
