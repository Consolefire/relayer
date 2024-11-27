package com.consolefire.relayer.writer.jdbc;

import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.util.jdbc.SqlDateUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MessageInsertPreparedStatementSetter<ID extends Serializable, M extends Message<ID>>
        implements PreparedStatementSetter<M> {

    protected final InsertQuery insertQuery;

    public MessageInsertPreparedStatementSetter(InsertQuery insertQuery) {
        this.insertQuery = insertQuery;
    }

    @Override
    public void setValues(M message, PreparedStatement preparedStatement) throws SQLException {

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.messageId.name()))) {
            setMessageId(message.getMessageId(), preparedStatement,
                    this.insertQuery.indexOf(getColumnName(Message.Fields.messageId.name())));
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.messageSequence.name()))) {
            preparedStatement.setLong(insertQuery.indexOf(getColumnName(Message.Fields.messageSequence.name())),
                    message.getMessageSequence());
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.groupId.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(Message.Fields.groupId.name())),
                    message.getGroupId());
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.payload.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(Message.Fields.payload.name())),
                    message.getPayload());
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.headers.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(Message.Fields.headers.name())),
                    message.getHeaders());
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.metadata.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(Message.Fields.metadata.name())),
                    message.getMetadata());
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.createdAt.name()))) {
            preparedStatement.setDate(insertQuery.indexOf(getColumnName(Message.Fields.createdAt.name())),
                    SqlDateUtils.toSqlDate(message.getCreatedAt()));
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(Message.Fields.updatedAt.name()))) {
            preparedStatement.setDate(insertQuery.indexOf(getColumnName(Message.Fields.updatedAt.name())),
                    SqlDateUtils.toSqlDate(message.getUpdatedAt()));
        }

        setAdditionalValues(message, preparedStatement);
    }

    protected abstract void setMessageId(ID messageId, PreparedStatement preparedStatement, int index)
            throws SQLException;

    protected abstract void setAdditionalValues(M message, PreparedStatement preparedStatement)
            throws SQLException;

    protected abstract String getColumnName(String fieldName);


}
