package com.galhelper.dialogue.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: UsageDto
 * Package: com.galhelper.dialogue.model.dto
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 18:10
 * @project galhelper-ai-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageDto {
    private int input;
    private int output;
    private int total;
}
