package com.galhelper.dialogue.controller;

import com.galhelper.dialogue.service.DialogueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @Operation(summary = "AI对话问答", description = "向AI助手提问并获取回答")
    @RequestMapping(value = "/ask", method = RequestMethod.POST)
    public String ask(
            @Parameter(description = "对话id", example = "101") @RequestParam Long dialogueId,
            @Parameter(description = "用户提问文本", example = "游戏报错了怎么办？") @RequestParam String askText) {
        return dialogueService.dialogue(dialogueId, askText);
    }
}
