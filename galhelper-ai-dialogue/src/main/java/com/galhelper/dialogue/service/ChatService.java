package com.galhelper.dialogue.service;

import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import reactor.core.publisher.Flux;

/**
 * ClassName: DialogueService
 * Package: com.galhelper.dialogue.service
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:54
 * @project galhelper-ai-service
 */
public interface ChatService {

    Flux<String> chatToAI(AiChatSessionInfo chatSessionInfo, String dialogueTxt);

    Long storeUserMessage(AiChatSessionInfo aiChatSessionInfo, Long parentMessageId, String messageText);

    Long storeAiMessage(AiChatSessionInfo aiChatSessionInfo, Long parentMessageId, String messageText);
}
