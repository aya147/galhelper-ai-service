package com.galhelper.dialogue.common.enums;

import lombok.Getter;

/**
 * ClassName: ResultCode
 * Package: com.galhelper.dialogue.common.enums
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 9:48
 * @project galhelper-ai-service
 */
@Getter
public enum ResultCode {

    // --- 成功区间 ---
    SUCCESS(0, "操作成功"),

    // --- 系统错误区间 (1000-1999) ---
    SYSTEM_ERROR(1000, "系统繁忙，请稍后再试"),
    PARAM_IS_INVALID(1001, "参数无效"),
    DATA_NOT_FOUND(1002, "数据不存在"),

    // --- 业务错误区间 (2000-2999, 针对 Gal Helper 业务) ---
    AI_SERVICE_TIMEOUT(2001, "AI 服务响应超时"),
    CONVERSATION_NOT_EXISTS(2002, "会话 ID 不存在"),
    CONTENT_VIOLATION(2003, "生成内容包含违规信息"),

    // --- 认证授权区间 (3000-3999) ---
    USER_NOT_LOGGED_IN(3001, "用户未登录"),
    PERMISSION_FORBIDDEN(3002, "权限不足");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
