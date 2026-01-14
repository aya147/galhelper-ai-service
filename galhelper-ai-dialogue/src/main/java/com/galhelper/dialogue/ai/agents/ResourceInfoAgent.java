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
 * ClassName: ResourceInfoAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 游戏资源站&资讯网站Agent
 *
 * @author aya
 * @date 2026/1/10 - 11:11
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderAiDialogue"
)
public interface ResourceInfoAgent extends BaseAgent {

    @Override
    @UserMessage("你是一个gal领域大神，请根据用户的需求，获取该资源相关的信息。用户的需求是：{{message}}")
    Flux<String> chat(@MemoryId Long conversationId, @V("message") String userMessage);

    @Override
    default UserIntentEnum getIdentify() { return UserIntentEnum.RESOURCE_AND_INFO; }
}
