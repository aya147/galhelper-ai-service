package com.galhelper.dialogue.service;

import com.galhelper.dialogue.common.model.DialogueContext;
import dev.langchain4j.service.TokenStream;

/**
 * ClassName: DialogueService
 * Package: com.galhelper.dialogue.service
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:54
 * @project galhelper-ai-service
 */
public interface DialogueService {

    DialogueContext handleDialogue(Long conversationId, String dialogueTxt);

    Long storeUserMessage(Long conversationId, String messageText);

    void storeAiMessage(Long conversationId, Long userMessageId, String messageText);
}
