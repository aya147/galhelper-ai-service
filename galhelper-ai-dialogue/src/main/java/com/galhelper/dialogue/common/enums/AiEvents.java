package com.galhelper.dialogue.common.enums;

import lombok.Getter;

/**
 * ClassName: AiEvent
 * Package: com.galhelper.dialogue.common.enums
 * Description: AI事件枚举，模仿deepseek
 *
 * @author aya
 * @date 2026/1/14 - 20:19
 * @project galhelper-ai-service
 */
@Getter
public enum AiEvents {

    READY("ready"),
    UPDATE_SESSION("update_session"),
    MESSAGE("message"),
    FINISH("finish"),
    TITLE("title"),
    CLOSE("close");

    private final String event;

    AiEvents(String event) {
        this.event = event;
    }
}
