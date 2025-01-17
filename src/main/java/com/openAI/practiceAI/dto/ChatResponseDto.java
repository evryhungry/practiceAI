package com.openAI.practiceAI.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponseDto {
    private String botMessage;   // AI가 응답하는 메시지
}