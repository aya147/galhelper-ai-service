package com.galhelper.dialogue.mapper;

import com.galhelper.dialogue.model.entity.AiChatSessionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AiChatSessionInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AiChatSessionInfo record);

    AiChatSessionInfo selectByPrimaryKey(Long id);

    List<AiChatSessionInfo> selectAll();

    int updateByPrimaryKey(AiChatSessionInfo record);

    /**
     * 更新用户会话的意图信息
     * @param id 会话id
     * @param userIntent 意图
     * @param updateTime 更新时间
     */
    void updateIntentById(@Param("id") Long id, @Param("userIntent") Integer userIntent,
                          @Param("updateTime") Date updateTime);

    /**
     * 根据主键更新会话信息，只更新非空的字段
     * @param updateRecord 更新的会话信息
     * @return
     */
    int updateByPrimaryKeySelective(AiChatSessionInfo updateRecord);

    /**
     * 根据会话编码查询会话信息（不包含记忆）
     * @param chatSessionCode 会话编码
     * @return
     */
    AiChatSessionInfo selectBySessionCode(String chatSessionCode);
}