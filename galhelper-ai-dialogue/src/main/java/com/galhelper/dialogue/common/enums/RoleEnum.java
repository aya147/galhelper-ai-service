package com.galhelper.dialogue.common.enums;

import lombok.Getter;

/**
 * ClassName: RoleEnum
 * Package: com.galhelper.dialogue.common.enums
 * Description:
 *
 * @author aya
 * @date 2026/1/14 - 21:55
 * @project galhelper-ai-service
 */
@Getter
public enum RoleEnum {

    USER("USER"),
    ASSISTANT("ASSISTANT");

    private final String code;

    RoleEnum(String code) {
        this.code = code;
    }
}
