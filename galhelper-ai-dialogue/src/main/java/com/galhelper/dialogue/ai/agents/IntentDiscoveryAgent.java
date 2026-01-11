package com.galhelper.dialogue.ai.agents;

import com.galhelper.dialogue.model.IntentResult;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * ClassName: IntentDiscoveryAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 用户意图识别的路由Agent
 *
 * @author aya
 * @date 2026/1/9 - 19:51
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProviderAiDialogue"
)
public interface IntentDiscoveryAgent {

    @UserMessage("你是一个gal领域大神，请就用户的对话内容，判断用户的真实需求。用户的对话内容：{{message}}")
    IntentResult discoverIntent(@MemoryId Long conversationId, @V("message") String userMessage);
}
