package com.galhelper.dialogue.ai.agents;

import com.galhelper.dialogue.common.enums.UserIntentEnum;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * ClassName: BaseAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 基础Agent
 *
 * @author aya
 * @date 2026/1/10 - 11:24
 * @project galhelper-ai-service
 */
public interface BaseAgent {
    /**
     * 进行对话
     * @param conversationId
     * @param message
     * @return
     */
    Flux<String> chat(@MemoryId Long conversationId, @UserMessage String message);
    /**
     * 获取当前Agent的标识
     * @return
     */
    UserIntentEnum getIdentify();
}
