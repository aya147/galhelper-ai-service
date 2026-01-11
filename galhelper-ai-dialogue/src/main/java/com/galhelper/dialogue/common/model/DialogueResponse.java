package com.galhelper.dialogue.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: DialogueResponse
 * Package: com.galhelper.dialogue.common.model
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 15:20
 * @project galhelper-ai-service
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogueResponse {
    private String type;    // 消息类型: "intent", "token", "meta", "error"
    private Object data;    // 具体数据内容
}
