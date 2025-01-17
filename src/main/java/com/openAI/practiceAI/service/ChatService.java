package com.openAI.practiceAI.service;

import com.openAI.practiceAI.dto.ChatRequestDto;
import com.openAI.practiceAI.dto.ChatResponseDto;
import com.openAI.practiceAI.entity.ChatMessage;
import com.openAI.practiceAI.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {

    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String ollamaModel;

    private final RestTemplate restTemplate;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.restTemplate = new RestTemplate();
    }

    public ChatResponseDto processUserMessage(ChatRequestDto requestDto) {
        // 1) 사용자 메시지를 DB에 저장할 준비
        String userMessage = requestDto.getUserMessage();

        // 2) Ollama로 챗봇(LLM) 호출해서 답변 얻기
        //    - Ollama에 /complete 또는 /chat 같은 API가 있을 수 있음(버전에 따라 차이)
        //    - 여기서는 /complete 예시
        String botResponse = callOllamaApi(userMessage);

//        // 3) DB에 저장
//        ChatMessage savedMessage = chatMessageRepository.save(ChatMessage.builder()
//                .userMessage(userMessage)
//                .botMessage(botResponse)
//                .createdAt(LocalDateTime.now())
//                .build());

        // 4) DTO로 응답
        return ChatResponseDto.builder()
                .botMessage(botResponse)
                .build();
    }

    private String callOllamaApi(String userMessage) {
        // Ollama API 엔드포인트 (예: /complete)
        String url = ollamaUrl + "/complete";

        // Ollama API에 보내줄 바디 (JSON)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", ollamaModel);
        requestBody.put("prompt", "User: " + userMessage + "\nBot:");
        // 프롬프트는 자유롭게 구성 가능
        // Ollama가 대답할 수 있도록 문맥을 줄 수도 있음

        // HTTP POST
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Ollama에서 {"completion": "..."} 형태로 온다고 가정
            Map<String, Object> responseMap = response.getBody();
            return (String) responseMap.getOrDefault("completion", "No response from Ollama");
        } else {
            return "Error: Ollama API call failed";
        }
    }
}
