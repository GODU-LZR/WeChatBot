package com.example.test.demos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userNickname;

    @Column(length = 2048, nullable = false)
    private String content;

    @Column(nullable = false)
    private String role;  // system, user, or assistant

    @Column(nullable = false)
    private LocalDateTime createTime;
}
