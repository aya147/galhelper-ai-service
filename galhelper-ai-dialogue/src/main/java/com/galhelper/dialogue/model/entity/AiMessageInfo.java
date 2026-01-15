package com.galhelper.dialogue.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Table(name = "ai_message_info")
@Schema(name = "消息记录信息类", description = "消息记录信息类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiMessageInfo implements Serializable {

    @Id
    @Schema(name = "id")
    @Column(name = "id")
    private Long id;

    @Schema(name = "会话id")
    @Column(name = "fk_session_id")
    private Long fkSessionId;

    @Schema(name = "在当前会话中的消息序列")
    @Column(name = "messageId")
    private Long messageId;

    @Schema(name = "当前会话的父消息序列")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(name = "角色 例如 USER、ASSISTANT")
    @Column(name = "role")
    private String role;

    @Schema(name = "消息内容")
    @Column(name = "message")
    private String message;

    @Schema(name = "创建时间")
    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "修改时间")
    @Column(name = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private static final long serialVersionUID = 1002L;


}