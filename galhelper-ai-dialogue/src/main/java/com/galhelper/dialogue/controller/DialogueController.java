package com.galhelper.dialogue.controller;

import com.galhelper.dialogue.common.model.DialogueContext;
import com.galhelper.dialogue.common.model.DialogueResponse;
import com.galhelper.dialogue.common.model.UsageDto;
import com.galhelper.dialogue.service.DialogueService;
import dev.langchain4j.model.output.TokenUsage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;

/**
 * ClassName: Dialogue
 * Package: com.galhelper.dialogue.controller
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:41
 * @project galhelper-ai-service
 */
@Tag(name = "gal求助吧 AI对话助手", description = "gal求助吧 AI对话助手")
@RestController
public class DialogueController {


    @Resource
    private DialogueService dialogueService;


    // TODO 1、返回给用户的消息出现了重复问题排查 2、优化prompt 3、重写响应式逻辑 4、可能会重新构造agent之间的调用逻辑
    @Operation(summary = "AI对话问答", description = "向AI助手提问并获取回答")
    @RequestMapping(value = "/ask", method = RequestMethod.POST)
    public SseEmitter ask(
            @Parameter(description = "会话id", example = "101") @RequestParam(required = false) Long conversationId,
            @Parameter(description = "用户提问文本", example = "游戏报错了怎么办？") @RequestParam String askText) {
        // 设置超时时间（根据 Gal 报错分析的复杂度，建议设为 2-5 分钟）
        SseEmitter emitter = new SseEmitter(120 * 1000L);
        StringBuilder aiReplyBuilder = new StringBuilder();

        try {
            // 2. 调用 Service 获取会话id和流 (流开始前，会话id已在 Context 中)
            DialogueContext context = dialogueService.handleDialogue(conversationId, askText);
            // 存储用户的messageInfo 到数据库中
            Long userMessageId = dialogueService.storeUserMessage(context.getConversationId(), askText);

            // 3. 【第一阶段】推送对话id
            // 这样前端刚开始的对话就有可以保存的会话Id，方便之后对话
            sendPacket(emitter, DialogueResponse.builder()
                    .type("conversationId")
                    .data(context.getConversationId())
                    .build());

            // 4. 【第二阶段】处理 Token 流
            context.getTokenStream()
                    .onPartialResponse(token -> {
                        aiReplyBuilder.append(token);
                        // 推送单个 Token
                        sendPacket(emitter, DialogueResponse.builder()
                                .type("token")
                                .data(token)
                                .build());
                    })
                    .onCompleteResponse(response -> {
                        String fullAiMessage = aiReplyBuilder.toString();
                        // 存储AI的messageInfo 到数据库中
                        dialogueService.storeAiMessage(context.getConversationId(), userMessageId, fullAiMessage);

                        // 【第三阶段】推送元数据 (如 Token 消耗)
                        // 提取 Token 使用情况
                        TokenUsage usage = response.tokenUsage();
                        sendPacket(emitter, DialogueResponse.builder()
                                .type("meta")
                                .data(new UsageDto(usage.inputTokenCount(), usage.outputTokenCount(), usage.totalTokenCount()))
                                .build());


                        emitter.complete();
                    })
                    .onError(error -> {
                        // 【异常阶段】推送错误信息
                        sendPacket(emitter, DialogueResponse.builder()
                                .type("error")
                                .data(error.getMessage())
                                .build());
                        emitter.completeWithError(error);
                    })
                    .start();
        } catch (Exception e) {
            // 捕获预处理阶段（如意图识别失败）的异常
            emitter.completeWithError(e);
        }
        return emitter;
    }


    /**
     * 封装发送逻辑，确保异常处理和 JSON 转化
     */
    private void sendPacket(SseEmitter emitter, DialogueResponse response) {
        try {
            // SSE 规范：data 后面必须是字符串，所以这里将对象转为 JSON
            // 也可以使用 emitter.send(SseEmitter.event().name(response.getType()).data(response))
            // 但统一在 data 里包含 type 更方便某些前端库解析
            emitter.send(SseEmitter.event().data(response));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
