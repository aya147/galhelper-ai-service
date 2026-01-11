package com.galhelper.dialogue.common.handler;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: JsonbTypeHandler
 * Package: com.galhelper.dialogue.common.handler
 * Description:
 * 专门用于处理 PostgreSQL JSONB 字段与 Java 对象映射的 TypeHandler。
 * 针对 Gal Helper 对话业务，特别强化了对 LangChain4j 中 List<ChatMessage> 的支持。
 *
 * @author aya
 * @date 2026/1/11 - 13:56
 * @project galhelper-ai-service
 */
@MappedJdbcTypes(JdbcType.OTHER) // 对应 PG 的 JSONB 在 JDBC 中的类型
@MappedTypes({Object.class, List.class}) // 对应实体类中的字段类型
public class JsonbTypeHandler extends BaseTypeHandler<List<ChatMessage>> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<ChatMessage> parameter, JdbcType jdbcType) throws SQLException {
        // ✅ 核心修正：使用 LangChain4j 官方工具进行 JSON 转换
        // 它会生成包含 "type": "USER" 等信息的标准 JSON
        String json = ChatMessageSerializer.messagesToJson(parameter);

        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        jsonObject.setValue(json);
        ps.setObject(i, jsonObject);
    }

    @Override
    public List<ChatMessage> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getString(columnName));
    }

    @Override
    public List<ChatMessage> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getString(columnIndex));
    }

    @Override
    public List<ChatMessage> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getString(columnIndex));
    }

    /**
     * 反序列化核心逻辑
     */
    private List<ChatMessage> toList(String json) {
        if (json == null || json.isBlank()) {
            return new java.util.ArrayList<>();
        }
        // ✅ 核心修正：使用官方反序列化工具
        // 它能根据 JSON 中的 type 自动还原成 UserMessage 或 AiMessage 对象
        return ChatMessageDeserializer.messagesFromJson(json);
    }
}
