package com.galhelper.dialogue.service.impl;

import com.galhelper.dialogue.ai.agents.BaseAgent;
import com.galhelper.dialogue.ai.agents.IntentDiscoveryAgent;
import com.galhelper.dialogue.common.enums.UserIntentEnum;
import com.galhelper.dialogue.common.model.DialogueContext;
import com.galhelper.dialogue.common.utils.CommonTools;
import com.galhelper.dialogue.common.utils.IdUtils;
import com.galhelper.dialogue.common.utils.TokenStreamUtils;
import com.galhelper.dialogue.mapper.AiConversationInfoMapper;
import com.galhelper.dialogue.mapper.AiMessageInfoMapper;
import com.galhelper.dialogue.model.IntentResult;
import com.galhelper.dialogue.model.entity.AiConversationInfo;
import com.galhelper.dialogue.model.entity.AiMessageInfo;
import com.galhelper.dialogue.service.DialogueService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
public class DialogueServiceImpl implements DialogueService {

    @Resource
    private AiConversationInfoMapper aiConversationInfoMapper;

    @Resource
    private AiMessageInfoMapper aiMessageInfoMapper;

    @Resource
    private IntentDiscoveryAgent intentDiscoveryAgent;

    private final Map<UserIntentEnum, BaseAgent> agentCache = new ConcurrentHashMap<>();

    // 构造函数：利用 Spring 的 List 注入，自动完成组装
    public DialogueServiceImpl(List<BaseAgent> allAgents) {
        for (BaseAgent agent : allAgents) {
            agentCache.put(agent.getIdentify(), agent);
        }
    }

    @Override
    public DialogueContext handleDialogue(Long conversationId, String dialogueTxt) {

        // 1、是否是第一次对话，如果不是记录一个新的对话，并进行意图分析
        if (Objects.isNull(conversationId)) {
            Long id = IdUtils.getNextId(CommonTools.getTableName(AiConversationInfo.class));
            conversationId = id;
            AiConversationInfo newAiConversation = new AiConversationInfo(id, null,
                    null, new Date(), null);
            aiConversationInfoMapper.insert(newAiConversation);
        }
        IntentResult intentResult = intentDiscoveryAgent.discoverIntent(conversationId, dialogueTxt);
        // 2、是否已经有明确的意图/需求，如果还没有，则接受指引
        if (Objects.isNull(intentResult.getIntent())
                || UserIntentEnum.VAGUE.equals(intentResult.getIntent())
                || UserIntentEnum.UNSUPPORTED.equals(intentResult.getIntent())) {
            // 将静态 String 转换为流
            // LangChain4j 最新版中，如果你想手动控制，可以利用其底层的 SseEmitter 逻辑
            // 或者简单地返回一个包装好的流
            return DialogueContext.builder()
                    .conversationId(conversationId)
                    .tokenStream(TokenStreamUtils.fromString(intentResult.getGuideMessage()))
                    .build();
        }
        // 3、根据意图/需求，使用不同的agent进行对话
        // 更新当前用户会话的意图信息
        aiConversationInfoMapper.updateIntentById(conversationId, intentResult.getIntent().getCode(), new Date());
        return DialogueContext.builder()
                .conversationId(conversationId)
                .tokenStream(this.route(UserIntentEnum.GAME_ERROR, conversationId, dialogueTxt))
                .build();
    }

    @Override
    public Long storeUserMessage(Long conversationId, String messageText) {
        AiMessageInfo aiMessageInfo = new AiMessageInfo();
        aiMessageInfo.setId(IdUtils.getNextId(CommonTools.getTableName(AiMessageInfo.class)));
        aiMessageInfo.setFkConversationId(conversationId);
        aiMessageInfo.setSenderType(1);
        aiMessageInfo.setReceiverType(2);
        aiMessageInfo.setMessage(messageText);
        aiMessageInfo.setSendTime(new Date());
        aiMessageInfoMapper.insert(aiMessageInfo);
        return aiMessageInfo.getId();
    }

    @Override
    public void storeAiMessage(Long conversationId, Long userMessageId, String messageText) {
        AiMessageInfo aiMessageInfo = new AiMessageInfo();
        aiMessageInfo.setId(IdUtils.getNextId(CommonTools.getTableName(AiMessageInfo.class)));
        aiMessageInfo.setFkConversationId(conversationId);
        aiMessageInfo.setFkMessageId(userMessageId);
        aiMessageInfo.setSenderType(2);
        aiMessageInfo.setReceiverType(1);
        aiMessageInfo.setMessage(messageText);
        aiMessageInfo.setSendTime(new Date());
        aiMessageInfoMapper.insert(aiMessageInfo);
    }

    public TokenStream route(UserIntentEnum intent, Long conversationId, String message) {
        return agentCache.get(intent).chat(conversationId, message);
    }
}
