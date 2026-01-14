package com.galhelper.dialogue.service.impl;

import com.galhelper.dialogue.common.utils.CommonTools;
import com.galhelper.dialogue.common.utils.IdUtils;
import com.galhelper.dialogue.mapper.AiChatSessionInfoMapper;
import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import com.galhelper.dialogue.model.vo.ChatSessionVo;
import com.galhelper.dialogue.service.ChatSessionService;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * ClassName: ChatSessionServiceImpl
 * Package: com.galhelper.dialogue.service.impl
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 11:16
 * @project galhelper-ai-service
 */
@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Resource
    private AiChatSessionInfoMapper aiChatSessionInfoMapper;

    @Override
    public ChatSessionVo createChatSession() {
        // 返回的VO
        ChatSessionVo chatSessionVo = new ChatSessionVo();

        // 创建一个新的会话
        Long id = IdUtils.getNextId(CommonTools.getTableName(AiChatSessionInfo.class));
        AiChatSessionInfo aiChatSessionInfo = new AiChatSessionInfo();
        aiChatSessionInfo.setId(id);
        // 使用UUID生成库的V7 来生成一个可以增长的唯一的会话编码
        UUID sessionCode = UuidCreator.getTimeOrderedEpoch();
        aiChatSessionInfo.setChatSessionCode(sessionCode.toString());
        Date currentTime = new Date();
        aiChatSessionInfo.setCreateTime(currentTime);
        aiChatSessionInfo.setUpdateTime(currentTime);
        aiChatSessionInfoMapper.insert(aiChatSessionInfo);

        chatSessionVo.setChatSessionCode(aiChatSessionInfo.getChatSessionCode());
        return chatSessionVo;
    }

    @Override
    public AiChatSessionInfo getChatSessionInfoByCode(String chatSessionCode) {
        AiChatSessionInfo aiChatSessionInfo = aiChatSessionInfoMapper.selectBySessionCode(chatSessionCode);
        return aiChatSessionInfo;
    }

    @Override
    public Integer updateChatSessionMessageId(Long currentMessageId, Long chatSessionId, Date updateTime) {
        AiChatSessionInfo aiChatSessionInfo = new AiChatSessionInfo();
        aiChatSessionInfo.setId(chatSessionId);
        aiChatSessionInfo.setCurrentMessageId(currentMessageId);
        aiChatSessionInfo.setUpdateTime(updateTime);
        return aiChatSessionInfoMapper.updateByPrimaryKeySelective(aiChatSessionInfo);
    }
}
