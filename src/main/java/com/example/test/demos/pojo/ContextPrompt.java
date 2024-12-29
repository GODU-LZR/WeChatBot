package com.example.test.demos.pojo;


import com.example.test.demos.utils.daoUtils.QueueStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.Queue;

@Entity
@Table(name = "context_prompts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContextPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userNickname;

    @Convert(converter = QueueStringConverter.class)
    @Column(name = "history", length = 20480) // 设置足够的长度以存储10条2048字符的消息
    private Queue<String> history = new LinkedList<>();
}
