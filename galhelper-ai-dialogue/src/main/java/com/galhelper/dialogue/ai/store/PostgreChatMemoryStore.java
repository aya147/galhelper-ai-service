package com.galhelper.dialogue.ai.store;

import com.galhelper.dialogue.mapper.AiConversationInfoMapper;
import com.galhelper.dialogue.model.entity.AiConversationInfo;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * ClassName: PostgreChatMemoryStore
 * Package: com.galhelper.dialogue.ai.store
 * Description: 将对话保存到PostgreSQL数据库中
 *
 * @author aya
 * @date 2026/1/10 - 20:36
 * @project galhelper-ai-service
 */
@Component
public class PostgreChatMemoryStore implements ChatMemoryStore {

    @Resource
    private AiConversationInfoMapper conversationMapper;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Long conversationId = (Long) memoryId;

        // 从数据库读取 AiConversationInfo 实体
        AiConversationInfo entity = conversationMapper.selectByPrimaryKey(conversationId);

        return (entity != null && entity.getConversationMemory() != null)
                ? entity.getConversationMemory()
                : Collections.emptyList();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        AiConversationInfo updateRecord = new AiConversationInfo();

        updateRecord.setId((Long) memoryId);
        updateRecord.setConversationMemory(messages);
        updateRecord.setUpdateTime(new Date());

        conversationMapper.updateByPrimaryKeySelective(updateRecord);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        updateMessages(memoryId, new ArrayList<>());
    }
}
