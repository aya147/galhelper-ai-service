package com.galhelper.dialogue.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.langchain4j.data.message.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "ai_conversation_info")
@Schema(name = "会话信息类", description = "会话信息类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiConversationInfo implements Serializable {

    @Id
    @Schema(name = "id")
    @Column(name = "id")
    private Long id;

    @Schema(name = "用户意图")
    @Column(name = "user_intent")
    private Integer userIntent;

    @Schema(name = "会话记忆")
    @Column(name = "conversation_memory")
    private List<ChatMessage> conversationMemory;

    @Schema(name = "创建时间")
    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "更新时间")
    @Column(name = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private static final long serialVersionUID = 1001L;

}