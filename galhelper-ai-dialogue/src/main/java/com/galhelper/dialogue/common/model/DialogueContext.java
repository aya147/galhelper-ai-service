package com.galhelper.dialogue.common.model;

import dev.langchain4j.service.TokenStream;
import lombok.Builder;
import lombok.Data;

/**
 * ClassName: DialogueContext
 * Package: com.galhelper.dialogue.common.model
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 15:42
 * @project galhelper-ai-service
 */
@Data
@Builder
public class DialogueContext {
    /**
     * 会话id
     */
    private Long conversationId;

    /**
     * langchain4j的AI回复流
     */
    private TokenStream tokenStream;
}
