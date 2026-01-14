package com.galhelper.dialogue.mapper;

import com.galhelper.dialogue.model.entity.AiMessageInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiMessageInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AiMessageInfo record);

    AiMessageInfo selectByPrimaryKey(Long id);

    List<AiMessageInfo> selectAll();

    int updateByPrimaryKey(AiMessageInfo record);
}