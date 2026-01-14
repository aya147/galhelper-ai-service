package com.galhelper.dialogue.ai.store;

import com.galhelper.dialogue.mapper.AiChatSessionInfoMapper;
import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import dev.langchain4j.data.message.ChatMessage;
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
    private AiChatSessionInfoMapper conversationMapper;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Long conversationId = (Long) memoryId;

        // 从数据库读取 AiConversationInfo 实体
        AiChatSessionInfo entity = conversationMapper.selectByPrimaryKey(conversationId);

        return (entity != null && entity.getChatSessionMemory() != null)
                ? entity.getChatSessionMemory()
                : Collections.emptyList();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        AiChatSessionInfo updateRecord = new AiChatSessionInfo();

        updateRecord.setId((Long) memoryId);
        updateRecord.setChatSessionMemory(messages);
        updateRecord.setUpdateTime(new Date());

        conversationMapper.updateByPrimaryKeySelective(updateRecord);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        updateMessages(memoryId, new ArrayList<>());
    }
}
