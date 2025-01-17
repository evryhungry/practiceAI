package com.openAI.practiceAI.controller;

import com.openAI.practiceAI.dto.ChatRequestDto;
import com.openAI.practiceAI.dto.ChatResponseDto;
import com.openAI.practiceAI.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {


    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponseDto sendMessage(@RequestBody ChatRequestDto requestDto) {
        return chatService.processUserMessage(requestDto);
    }
}
