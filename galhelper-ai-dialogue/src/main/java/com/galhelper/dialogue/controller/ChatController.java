package com.galhelper.dialogue.controller;

import com.galhelper.dialogue.common.enums.AiEvents;
import com.galhelper.dialogue.common.model.BaseController;
import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import com.galhelper.dialogue.service.ChatService;
import com.galhelper.dialogue.service.ChatSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName: Dialogue
 * Package: com.galhelper.dialogue.controller
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:41
 * @project galhelper-ai-service
 */
@Slf4j
@Tag(name = "gal求助吧 AI对话助手对话", description = "对话")
@RequestMapping("/chat")
@RestController
public class ChatController extends BaseController {


    @Resource
    private ChatService chatService;

    @Resource
    private ChatSessionService chatSessionService;

//    // TODO 1、返回给用户的消息出现了重复问题排查 2、优化prompt 3、重写响应式逻辑 4、可能会重新构造agent之间的调用逻辑
    @Operation(summary = "AI对话问答", description = "向AI助手提问并获取回答")
    @RequestMapping(value = "/completion", method = RequestMethod.POST, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> ask(
            @Parameter(description = "会话编码", example = "134c1ca1-62fc-44f4-aa6f-56e8a8736955")
            @RequestParam String chatSessionCode,
            @Parameter(description = "用户提问文本", example = "游戏报错了怎么办？") @RequestParam String askText) {

        // --- 事件 1: ready ---
        AiChatSessionInfo chatSessionInfo = chatSessionService.getChatSessionInfoByCode(chatSessionCode);
        log.info("对话消息编码:{}，当前的会话序列：{}", chatSessionInfo.getChatSessionCode(), chatSessionInfo.getCurrentMessageId());
        Long currentMessageId = chatSessionInfo.getCurrentMessageId();
        Long nextMessageId = Objects.isNull(chatSessionInfo.getCurrentMessageId())? 1L : chatSessionInfo.getCurrentMessageId() + 1;
        Flux<ServerSentEvent<Object>> readyEvent = Flux.just(
                ServerSentEvent.builder()
                        .event(AiEvents.READY.getEvent())
                        .data(Map.of("request_message_id", nextMessageId, "response_message_id", nextMessageId + 1))
                        .build()
        );

        // --- 事件 2: update_session ---
        Date updateChatSessionMessageIdDate = new Date();
        chatService.storeUserMessage(chatSessionInfo, currentMessageId, askText);
        chatService.storeAiMessage(chatSessionInfo, nextMessageId, null);

        chatSessionService.updateChatSessionMessageId(nextMessageId + 1, chatSessionInfo.getId(), updateChatSessionMessageIdDate);
        Flux<ServerSentEvent<Object>> updateSessionEvent = Flux.just(
                ServerSentEvent.builder()
                        .event(AiEvents.UPDATE_SESSION.getEvent())
                        .data(Map.of("updated_at", updateChatSessionMessageIdDate))
                        .build()
        );

        // --- 事件 3: AI 内容流 (message) ---
        // 这里使用 assistant.chat 返回的 Flux<String>
        Flux<ServerSentEvent<Object>> contentStream = chatService.chatToAI(chatSessionInfo, askText)
                .map(token -> ServerSentEvent.builder()
                        // 注意：DeepSeek 的内容流有时不带 event 标签，默认为 message
                        .data(Map.of("p", "content", "v", token))
                        .build());

        // --- 事件 4: finish ---
        Flux<ServerSentEvent<Object>> finishEvent = Flux.just(
                ServerSentEvent.builder()
                        .event(AiEvents.FINISH.getEvent())
                        .data(Collections.emptyMap())
                        .build()
        );

        // --- 事件 5: update_session ---

        // --- 事件 6: close ---
        Flux<ServerSentEvent<Object>> closeEvent = Flux.just(
                ServerSentEvent.builder()
                        .event(AiEvents.CLOSE.getEvent())
                        .data(Map.of("click_behavior", "none"))
                        .build()
        );

        // 3. 按照 DeepSeek 的严格顺序合并流
        return Flux.concat(
                readyEvent,
                updateSessionEvent,
                contentStream,
                finishEvent,
                closeEvent
        ).onErrorResume(e -> Flux.just(
                ServerSentEvent.builder()
                        .event("error")
                        .data(Map.of("message", e.getMessage()))
                        .build()
        ));
//
//        // 设置超时时间（根据 Gal 报错分析的复杂度，建议设为 2-5 分钟）
//        SseEmitter emitter = new SseEmitter(120 * 1000L);
//        StringBuilder aiReplyBuilder = new StringBuilder();
//
//        try {
//            // 2. 调用 Service 获取会话id和流 (流开始前，会话id已在 Context 中)
//            DialogueContext context = dialogueService.handleDialogue(conversationId, askText);
//            // 存储用户的messageInfo 到数据库中
//            Long userMessageId = dialogueService.storeUserMessage(context.getConversationId(), askText);
//
//            // 3. 【第一阶段】推送对话id
//            // 这样前端刚开始的对话就有可以保存的会话Id，方便之后对话
//            sendPacket(emitter, DialogueResponse.builder()
//                    .type("conversationId")
//                    .data(context.getConversationId())
//                    .build());
//
//            // 4. 【第二阶段】处理 Token 流
//            context.getTokenStream()
//                    .onPartialResponse(token -> {
//                        aiReplyBuilder.append(token);
//                        // 推送单个 Token
//                        sendPacket(emitter, DialogueResponse.builder()
//                                .type("token")
//                                .data(token)
//                                .build());
//                    })
//                    .onCompleteResponse(response -> {
//                        String fullAiMessage = aiReplyBuilder.toString();
//                        // 存储AI的messageInfo 到数据库中
//                        dialogueService.storeAiMessage(context.getConversationId(), userMessageId, fullAiMessage);
//
//                        // 【第三阶段】推送元数据 (如 Token 消耗)
//                        // 提取 Token 使用情况
//                        TokenUsage usage = response.tokenUsage();
//                        sendPacket(emitter, DialogueResponse.builder()
//                                .type("meta")
//                                .data(new UsageDto(usage.inputTokenCount(), usage.outputTokenCount(), usage.totalTokenCount()))
//                                .build());
//
//
//                        emitter.complete();
//                    })
//                    .onError(error -> {
//                        // 【异常阶段】推送错误信息
//                        sendPacket(emitter, DialogueResponse.builder()
//                                .type("error")
//                                .data(error.getMessage())
//                                .build());
//                        emitter.completeWithError(error);
//                    })
//                    .start();
//        } catch (Exception e) {
//            // 捕获预处理阶段（如意图识别失败）的异常
//            emitter.completeWithError(e);
//        }
//        return emitter;
    }


//    /**
//     * 封装发送逻辑，确保异常处理和 JSON 转化
//     */
//    private void sendPacket(SseEmitter emitter, DialogueResponse response) {
//        try {
//            // SSE 规范：data 后面必须是字符串，所以这里将对象转为 JSON
//            // 也可以使用 emitter.send(SseEmitter.event().name(response.getType()).data(response))
//            // 但统一在 data 里包含 type 更方便某些前端库解析
//            emitter.send(SseEmitter.event().data(response));
//        } catch (IOException e) {
//            emitter.completeWithError(e);
//        }
//    }
}
