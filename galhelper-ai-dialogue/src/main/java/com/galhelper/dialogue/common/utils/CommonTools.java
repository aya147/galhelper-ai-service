package com.galhelper.dialogue.common.utils;

import jakarta.persistence.Table;

/**
 * ClassName: CommonTools
 * Package: com.galhelper.dialogue.common.utils
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 14:42
 * @project galhelper-ai-service
 */
public class CommonTools {

    public static String getTableName(Class<?> clazz){
        return clazz.getAnnotation(Table.class).name();
    }
}
