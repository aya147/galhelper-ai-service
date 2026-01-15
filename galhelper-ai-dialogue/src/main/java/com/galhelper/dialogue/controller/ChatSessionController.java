package com.galhelper.dialogue.controller;

import com.galhelper.dialogue.common.model.BaseController;
import com.galhelper.dialogue.common.model.Result;
import com.galhelper.dialogue.model.vo.ChatSessionVo;
import com.galhelper.dialogue.service.ChatSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ChatSessionController
 * Package: com.galhelper.dialogue.controller
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 9:39
 * @project galhelper-ai-service
 */
@Tag(name = "AI对话助手会话接口", description = "会话")
@Slf4j
@RequestMapping("/chat_session")
@RestController
public class ChatSessionController extends BaseController {


    @Resource
    private ChatSessionService chatSessionService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<ChatSessionVo> createChatSession() {
        ChatSessionVo chatSession = chatSessionService.createChatSession();
        log.info("创建会话成功，会话编码：{}", chatSession.getChatSessionCode());
        return this.success(chatSession);
    }
}
