package com.galhelper.dialogue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: CommonMapper
 * Package: com.galhelper.dialogue.mapper
 * Description:
 *
 * @author aya
 * @date 2026/1/11 - 14:33
 * @project galhelper-ai-service
 */
@Mapper
public interface CommonMapper {

    /**
     * 调用PostgreSQL 函数获取下一个ID
     * @param tableName 数据库表名
     * @return 下一个可用的 Long ID
     */
    @Select("SELECT get_next_id(#{tableName})")
    Long getNextId(@Param("tableName") String tableName);
}
