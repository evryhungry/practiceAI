package com.openAI.practiceAI.repository;

import com.openAI.practiceAI.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}