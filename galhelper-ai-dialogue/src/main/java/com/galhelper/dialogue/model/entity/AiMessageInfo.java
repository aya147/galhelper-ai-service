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
    @Column(name = "fk_conversation_id")
    private Long fkConversationId;

    @Schema(name = "对应的消息id，AI回应用户")
    @Column(name = "fk_message_id")
    private Long fkMessageId;

    @Schema(name = "发送方类型 1用户|2AI")
    @Column(name = "sender_type")
    private Integer senderType;

    @Schema(name = "接收方类型 1用户|2AI")
    @Column(name = "receiver_type")
    private Integer receiverType;

    @Schema(name = "消息内容")
    @Column(name = "message")
    private String message;

    @Schema(name = "发送时间")
    @Column(name = "send_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;


    private static final long serialVersionUID = 1002L;


}