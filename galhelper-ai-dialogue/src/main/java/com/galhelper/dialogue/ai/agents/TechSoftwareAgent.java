package com.galhelper.dialogue.ai.agents;

import com.galhelper.dialogue.common.enums.UserIntentEnum;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * ClassName: TechSoftwareAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 游戏相关软件使用和技术探讨Agent
 *
 * @author aya
 * @date 2026/1/10 - 11:12
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderAiDialogue"
)
public interface TechSoftwareAgent extends BaseAgent {

    @Override
    @UserMessage("你是一个游戏开发专家，请根据用户的对话内容，给出游戏相关软件使用和技术探讨的解决方案。用户的对话内容：{{message}}")
    Flux<String> chat(@MemoryId Long conversationId, @V("message") String message);

    @Override
    default UserIntentEnum getIdentify() { return UserIntentEnum.TECH_SOFTWARE; }
}
