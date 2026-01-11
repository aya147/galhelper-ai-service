package com.galhelper.dialogue.common.utils;

import com.galhelper.dialogue.mapper.CommonMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: IdUtils
 * Package: com.galhelper.dialogue.common.utils
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 14:35
 * @project galhelper-ai-service
 */
@Component
public class IdUtils {

    // 静态持有 Mapper 引用
    private static CommonMapper commonMapper;

    @Autowired
    private CommonMapper autowiredMapper;

    /**
     * Spring 初始化后，将注入的 Bean 赋值给静态变量
     */
    @PostConstruct
    public void init() {
        IdUtils.commonMapper = autowiredMapper;
    }

    /**
     * 核心静态方法：获取下一个 ID
     * @param tableName 表名（需要与数据库 sys_sequence 表中的 seq_name 一致）
     * @return ID
     */
    public static Long getNextId(String tableName) {
        if (commonMapper == null) {
            throw new RuntimeException("IdUtils 尚未初始化，Mapper 注入失败");
        }
        return commonMapper.getNextId(tableName);
    }
}
