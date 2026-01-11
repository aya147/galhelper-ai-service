package com.galhelper.dialogue.ai.agents;

import com.galhelper.dialogue.common.enums.UserIntentEnum;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * ClassName: GameErrorAgent
 * Package: com.galhelper.dialogue.ai.agents
 * Description: 游戏安装&报错处理Agent
 *
 * @author aya
 * @date 2026/1/10 - 11:07
 * @project galhelper-ai-service
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderAiDialogue"
)
public interface GameErrorAgent extends BaseAgent {

    @Override
    @UserMessage("你是一个游戏开发专家，请根据用户的对话内容，给出游戏安装&运行报错的解决方案。用户的对话内容：{{message}}")
    TokenStream chat(@MemoryId Long conversationId, @V("message") String message);

    @Override
    default UserIntentEnum getIdentify() { return UserIntentEnum.GAME_ERROR; }
}
