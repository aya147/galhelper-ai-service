package com.galhelper.dialogue.ai.config;

import com.galhelper.dialogue.ai.store.PostgreChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: AiDialogueConfig
 * Package: com.galhelper.dialogue.ai.config
 * Description: AI对话相关配置
 *
 * @author aya
 * @date 2026/1/10 - 20:32
 * @project galhelper-ai-service
 */
@Configuration
public class AiDialogueConfig {

    @Resource
    private PostgreChatMemoryStore postgreChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProviderAiDialogue() {

        return memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(50)
                        .chatMemoryStore(postgreChatMemoryStore)
                        .build();
    }
}
