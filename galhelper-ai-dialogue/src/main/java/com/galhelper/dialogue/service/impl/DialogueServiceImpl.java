package com.galhelper.dialogue.service.impl;

import com.galhelper.dialogue.service.DialogueService;
import org.springframework.stereotype.Service;

/**
 * ClassName: DialogueServiceImpl
 * Package: com.galhelper.dialogue.service.impl
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 18:58
 * @project galhelper-ai-service
 */
@Service
public class DialogueServiceImpl implements DialogueService {
    @Override
    public String dialogue(Long dialogueId, String dialogueTxt) {

        // TODO 具体业务代码逻辑的实现

        System.out.println("dialogueId:"+dialogueId + "dialogueTxt:"+dialogueTxt);
        return "Hello world!";
    }
}
