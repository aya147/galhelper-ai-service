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
 * ClassName: GameRecommendAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 游戏推荐&找符合条件的游戏Agent
 *
 * @author aya
 * @date 2026/1/10 - 11:09
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderAiDialogue"
)
public interface GameRecommendAgent extends BaseAgent {

    @Override
    @UserMessage("你是一个gal领域大神，请根据用户的对话内容，推荐一个合适的游戏。用户的对话内容：{{message}}")
    Flux<String> chat(@MemoryId Long conversationId, @V("message") String userMessage);

    @Override
    default UserIntentEnum getIdentify() { return UserIntentEnum.GAME_RECOMMEND; }
}
