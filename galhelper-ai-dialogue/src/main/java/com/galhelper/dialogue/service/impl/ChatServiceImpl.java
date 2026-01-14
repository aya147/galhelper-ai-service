package com.galhelper.dialogue.service.impl;

import com.galhelper.dialogue.ai.agents.BaseAgent;
import com.galhelper.dialogue.ai.agents.IntentDiscoveryAgent;
import com.galhelper.dialogue.common.enums.RoleEnum;
import com.galhelper.dialogue.common.enums.UserIntentEnum;
import com.galhelper.dialogue.common.utils.CommonTools;
import com.galhelper.dialogue.common.utils.IdUtils;
import com.galhelper.dialogue.common.utils.TokenStreamUtils;
import com.galhelper.dialogue.mapper.AiChatSessionInfoMapper;
import com.galhelper.dialogue.mapper.AiMessageInfoMapper;
import com.galhelper.dialogue.model.IntentResult;
import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import com.galhelper.dialogue.model.entity.AiMessageInfo;
import com.galhelper.dialogue.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: DialogueServiceImpl
 * Package: com.galhelper.dialogue.service.impl
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:58
 * @project galhelper-ai-service
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private AiChatSessionInfoMapper aiChatSessionInfoMapper;

    @Resource
    private AiMessageInfoMapper aiMessageInfoMapper;

    @Resource
    private IntentDiscoveryAgent intentDiscoveryAgent;

    private final Map<UserIntentEnum, BaseAgent> agentCache = new ConcurrentHashMap<>();

    // 构造函数：利用 Spring 的 List 注入，自动完成组装
    public ChatServiceImpl(List<BaseAgent> allAgents) {
        for (BaseAgent agent : allAgents) {
            agentCache.put(agent.getIdentify(), agent);
        }
    }

    @Override
    public Flux<String> chatToAI(AiChatSessionInfo chatSessionInfo, String dialogueTxt) {

        IntentResult intentResult = intentDiscoveryAgent.discoverIntent(chatSessionInfo.getId(), dialogueTxt);
        // 1、是否已经有明确的意图/需求，如果还没有，则接受指引
        if (Objects.isNull(intentResult.getIntent())
                || UserIntentEnum.VAGUE.equals(intentResult.getIntent())
                || UserIntentEnum.UNSUPPORTED.equals(intentResult.getIntent())) {
            // 将静态 String 转换为流
            return Flux.just(intentResult.getGuideMessage());
        }
        // 2、根据意图/需求，使用不同的agent进行对话
        // 更新当前用户会话的意图信息
        aiChatSessionInfoMapper.updateIntentById(chatSessionInfo.getId(), intentResult.getIntent().getCode(), new Date());
        return this.route(intentResult.getIntent(), chatSessionInfo.getId(), dialogueTxt);
    }

    @Override
    public Long storeUserMessage(AiChatSessionInfo chatSessionInfo, Long parentMessageId, String messageText) {
        return this.storeMessageInfo(chatSessionInfo, parentMessageId, messageText, RoleEnum.USER);
    }

    @Override
    public Long storeAiMessage(AiChatSessionInfo aiChatSessionInfo, Long parentMessageId, String messageText) {
        return this.storeMessageInfo(aiChatSessionInfo, parentMessageId, messageText, RoleEnum.ASSISTANT);
    }

    private Long storeMessageInfo(AiChatSessionInfo chatSessionInfo, Long parentMessageId, String messageText, RoleEnum role) {
        Long messageId = Objects.isNull(parentMessageId)? 1L : parentMessageId + 1;

        AiMessageInfo aiMessageInfo = new AiMessageInfo();
        aiMessageInfo.setId(IdUtils.getNextId(CommonTools.getTableName(AiMessageInfo.class)));
        aiMessageInfo.setFkSessionId(chatSessionInfo.getId());
        aiMessageInfo.setMessageId(messageId);
        aiMessageInfo.setParentId(parentMessageId);
        aiMessageInfo.setRole(role.getCode());
        aiMessageInfo.setMessage(messageText);
        aiMessageInfo.setCreateTime(new Date());
        aiMessageInfoMapper.insert(aiMessageInfo);
        return aiMessageInfo.getMessageId();
    }

    public Flux<String> route(UserIntentEnum intent, Long conversationId, String message) {
        return agentCache.get(intent).chat(conversationId, message);
    }
}
