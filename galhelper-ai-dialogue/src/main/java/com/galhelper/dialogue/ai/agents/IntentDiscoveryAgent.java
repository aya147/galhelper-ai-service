package com.galhelper.dialogue.ai.agents;

import com.galhelper.dialogue.common.enums.UserIntentEnum;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * ClassName: IntentDiscoveryAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 19:51
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel"
)
public interface IntentDiscoveryAgent {

    @UserMessage("你是一个gal领域大神，请就用户的对话内容，判断用户的真实需求。用户的对话内容：{{message}}")
    UserIntentEnum discoverIntent(@V("message") String userMessage);
}
