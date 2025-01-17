package com.openAI.practiceAI.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequestDto {
    private String userMessage;  // 사용자가 전송하는 메시지
}