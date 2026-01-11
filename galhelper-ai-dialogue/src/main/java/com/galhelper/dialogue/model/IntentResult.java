package com.galhelper.dialogue.model;

import com.galhelper.dialogue.common.enums.UserIntentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: IntentResult
 * Package: com.galhelper.dialogue.model
 * Description: 意图识别结果
 *
 * @author aya
 * @date 2026/1/10 - 12:32
 * @project galhelper-ai-service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentResult {
    /**
     * 用户意图枚举
     */
    private UserIntentEnum intent; // 如果识别成功，存入枚举
    /**
     * 引导信息
     */
    private String guideMessage;   // 如果需要引导用户，存入引导语
    /**
     * 是否需要直接路由到子 Agent
     */
    private boolean shouldRedirect; // 是否直接路由到子 Agent
}
