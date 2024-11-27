package com.consolefire.relayer.testutils.data;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.model.SidelinedMessage;
import com.consolefire.relayer.util.jdbc.ColumnInfo;
import com.consolefire.relayer.util.jdbc.JavaFieldInfo;

import java.sql.JDBCType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestMessageStoreMetadata {

    public static final String SCHEMA_MESSAGE_STORE = "MESSAGE_STORE";

    public static final String TABLE_OUTBOUND_MESSAGE = "outbound_message";
    public static final String TABLE_SIDELINED_MESSAGE = "sidelined_message";

    public static final Map<JavaFieldInfo, ColumnInfo> OUTBOUND_MESSAGE_COLUMN_MAPPINGS = new HashMap<>();

    public static final Map<JavaFieldInfo, ColumnInfo> SIDELINED_MESSAGE_COLUMN_MAPPINGS = new HashMap<>();

    public static final String CN_MESSAGE_ID = "message_id";
    public static final String CN_MESSAGE_SEQUENCE = "message_sequence";
    public static final String CN_GROUP_ID = "group_id";
    public static final String CN_CHANNEL_NAME = "channel_name";
    public static final String CN_PAYLOAD = "payload";
    public static final String CN_HEADERS = "headers";
    public static final String CN_METADATA = "metadata";
    public static final String CN_CREATED_AT = "created_at";
    public static final String CN_UPDATED_AT = "updated_at";
    public static final String CN_STATE = "state";
    public static final String CN_RELAYED_AT = "relayed_at";
    public static final String CN_RELAY_COUNT = "relay_count";
    public static final String CN_RETRY_COUNT = "retry_count";
    public static final String CN_LAST_TRIED_AT = "last_tried_at";

    static {
        JavaFieldInfo fieldInfoOutboundMessage = JavaFieldInfo.builder()
                .enclosingType(OutboundMessage.class)
                .fieldType(Object.class)
                .fieldName(Message.Fields.messageId.name())
                .fieldType(Object.class)
                .build();
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.messageId.name()),
                ColumnInfo.builder().columnName(CN_MESSAGE_ID).javaType(UUID.class).jdbcType(JDBCType.VARCHAR).serialNumber(1).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.messageSequence.name()),
                ColumnInfo.builder().columnName(CN_MESSAGE_SEQUENCE).javaType(Long.class).jdbcType(JDBCType.BIGINT).serialNumber(2).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.groupId.name()),
                ColumnInfo.builder().columnName(CN_GROUP_ID).javaType(String.class).jdbcType(JDBCType.VARCHAR).serialNumber(3).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(OutboundMessage.Fields.channelName.name()),
                ColumnInfo.builder().columnName(CN_CHANNEL_NAME).javaType(String.class).jdbcType(JDBCType.VARCHAR).serialNumber(4).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.payload.name()),
                ColumnInfo.builder().columnName(CN_PAYLOAD).javaType(String.class).jdbcType(JDBCType.BLOB).serialNumber(5).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.headers.name()),
                ColumnInfo.builder().columnName(CN_HEADERS).javaType(String.class).jdbcType(JDBCType.BLOB).serialNumber(6).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.metadata.name()),
                ColumnInfo.builder().columnName(CN_METADATA).javaType(String.class).jdbcType(JDBCType.BLOB).serialNumber(7).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(OutboundMessage.Fields.state.name()),
                ColumnInfo.builder().columnName(CN_STATE).javaType(String.class).jdbcType(JDBCType.VARCHAR).serialNumber(8).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(OutboundMessage.Fields.relayedAt.name()),
                ColumnInfo.builder().columnName(CN_RELAYED_AT).javaType(Date.class).jdbcType(JDBCType.TIMESTAMP_WITH_TIMEZONE).serialNumber(9).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(OutboundMessage.Fields.relayCount.name()),
                ColumnInfo.builder().columnName(CN_RELAY_COUNT).javaType(Integer.class).jdbcType(JDBCType.INTEGER).serialNumber(10).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.createdAt.name()),
                ColumnInfo.builder().columnName(CN_CREATED_AT).javaType(Date.class).jdbcType(JDBCType.TIMESTAMP_WITH_TIMEZONE).serialNumber(11).build());
        OUTBOUND_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoOutboundMessage.withFieldName(Message.Fields.updatedAt.name()),
                ColumnInfo.builder().columnName(CN_UPDATED_AT).javaType(Date.class).jdbcType(JDBCType.TIMESTAMP_WITH_TIMEZONE).serialNumber(12).build());

        JavaFieldInfo fieldInfoSidelinedMessage = JavaFieldInfo.builder()
                .enclosingType(SidelinedMessage.class)
                .fieldType(Object.class)
                .fieldName(Message.Fields.messageId.name())
                .fieldType(Object.class)
                .build();
        SIDELINED_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoSidelinedMessage.withFieldName(Message.Fields.messageId.name()),
                ColumnInfo.builder().columnName(CN_MESSAGE_ID).javaType(UUID.class).jdbcType(JDBCType.VARCHAR).serialNumber(1).build());
        SIDELINED_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoSidelinedMessage.withFieldName(Message.Fields.groupId.name()),
                ColumnInfo.builder().columnName(CN_GROUP_ID).javaType(String.class).jdbcType(JDBCType.VARCHAR).serialNumber(2).build());
        SIDELINED_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoSidelinedMessage.withFieldName(SidelinedMessage.Fields.retryCount.name()),
                ColumnInfo.builder().columnName(CN_RETRY_COUNT).javaType(Integer.class).jdbcType(JDBCType.INTEGER).serialNumber(3).build());
        SIDELINED_MESSAGE_COLUMN_MAPPINGS.put(
                fieldInfoSidelinedMessage.withFieldName(SidelinedMessage.Fields.lastTriedAt.name()),
                ColumnInfo.builder().columnName(CN_LAST_TRIED_AT).javaType(Date.class).jdbcType(JDBCType.TIMESTAMP_WITH_TIMEZONE).serialNumber(4).build());
    }
}
