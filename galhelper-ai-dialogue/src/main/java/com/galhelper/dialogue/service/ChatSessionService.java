package com.galhelper.dialogue.service;

import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import com.galhelper.dialogue.model.vo.ChatSessionVo;

import java.util.Date;

/**
 * ClassName: ChatSessionService
 * Package: com.galhelper.dialogue.service
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 11:14
 * @project galhelper-ai-service
 */
public interface ChatSessionService {

    /**
     * 创建一个新的会话
     *
     * @return
     */
    ChatSessionVo createChatSession();

    /**
     * 根据会话编码查询当前会话信息，不包括memory
     * @param chatSessionCode 会话编码
     * @return
     */
    AiChatSessionInfo getChatSessionInfoByCode(String chatSessionCode);

    /**
     * 更新当前会话的当前消息序列
     * @param currentMessageId 当前消息序列
     * @param chatSessionId id
     * @return
     */
    Integer updateChatSessionMessageId(Long currentMessageId, Long chatSessionId, Date updateTime);
}
