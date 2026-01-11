package com.galhelper.dialogue.common.utils;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.FinishReason;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.tool.ToolExecution;

import java.util.List;
import java.util.function.Consumer;

/**
 * ClassName: TokenStreamUtils
 * Package: com.galhelper.dialogue.common.utils
 * Description:
 *
 * @author aya
 * @date 2026/1/10 - 12:50
 * @project galhelper-ai-service
 */
public class TokenStreamUtils {
    /**
     * 将静态字符串包装为 TokenStream
     * @param content 需要输出的内容
     * @return TokenStream 实例
     */
    public static TokenStream fromString(String content) {
        return new TokenStream() {
            private Consumer<String> partialHandler;
            private Consumer<ChatResponse> completeHandler;
            private Consumer<Throwable> errorHandler;
            private boolean shouldIgnoreErrors = false;

            @Override
            public TokenStream onPartialResponse(Consumer<String> handler) {
                this.partialHandler = handler;
                return this;
            }

            @Override
            public TokenStream onRetrieved(Consumer<List<Content>> consumer) {
                // 静态文本不涉及 RAG，忽略
                return this;
            }

            @Override
            public TokenStream onToolExecuted(Consumer<ToolExecution> consumer) {
                // 静态文本不涉及工具调用，忽略
                return null;
            }

            @Override
            public TokenStream onCompleteResponse(Consumer<ChatResponse> handler) {
                this.completeHandler = handler;
                return this;
            }

            @Override
            public TokenStream onError(Consumer<Throwable> handler) {
                this.errorHandler = handler;
                return this;
            }

            @Override
            public TokenStream ignoreErrors() {
                this.shouldIgnoreErrors = true;
                return this;
            }

            @Override
            public void start() {
                try {
                    if (partialHandler != null) {
                        // 如果希望有打字机效果，可以在这里写个循环+Thread.sleep
                        // 这里演示直接一次性发送
                        partialHandler.accept(content);
                    }
                    if (completeHandler != null) {
                        // 构造一个简单的响应对象标记结束
                        completeHandler.accept(ChatResponse.builder()
                                .aiMessage(AiMessage.from(content))
                                .finishReason(FinishReason.STOP)
                                .build());
                    }
                } catch (Exception e) {
                    if (errorHandler != null) {
                        errorHandler.accept(e);
                    }
                }
            }
        };
    }
}
