package com.galhelper.dialogue.mapper;

import com.galhelper.dialogue.model.entity.AiMessageInfo;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AiMessageInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AiMessageInfo record);

    AiMessageInfo selectByPrimaryKey(Long id);

    List<AiMessageInfo> selectAll();

    int updateByPrimaryKey(AiMessageInfo record);

    /**
     * 更新AI消息内容
     *
     * @param fkSessionId 会话id
     * @param MessageId 消息序列id
     * @param message 消息内容
     * @param updateTime  时间
     * @return
     */
    int updateMessageContentBySession(@Param("fkSessionId") Long fkSessionId, @Param("messageId") Long MessageId,
                                      @Param("message") String message, @Param("updateTime") Date updateTime);
}