package com.consolefire.relayer.writer.jdbc;

import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.util.jdbc.SqlDateUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public abstract class OutboundMessageInsertPreparedStatementSetter<ID extends Serializable>
        extends MessageInsertPreparedStatementSetter<ID, OutboundMessage<ID>> {

    private final Map<String, String> fieldColumnMap;

    public OutboundMessageInsertPreparedStatementSetter(InsertQuery insertQuery, Map<String, String> fieldColumnMap) {
        super(insertQuery);
        this.fieldColumnMap = fieldColumnMap;
    }

    @Override
    protected void setAdditionalValues(OutboundMessage<ID> message, PreparedStatement preparedStatement)
            throws SQLException {
        if (insertQuery.getColumnIndexes().containsKey(getColumnName(OutboundMessage.Fields.channelName.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(OutboundMessage.Fields.channelName.name())),
                    message.getChannelName());
        }
        if (insertQuery.getColumnIndexes().containsKey(getColumnName(OutboundMessage.Fields.state.name()))) {
            preparedStatement.setString(insertQuery.indexOf(getColumnName(OutboundMessage.Fields.state.name())),
                    Optional.ofNullable(message.getState().name()).orElse(null));
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(OutboundMessage.Fields.relayedAt.name()))) {
            preparedStatement.setDate(insertQuery.indexOf(getColumnName(OutboundMessage.Fields.relayedAt.name())),
                    SqlDateUtils.toSqlDate(message.getRelayedAt()));
        }

        if (insertQuery.getColumnIndexes().containsKey(getColumnName(OutboundMessage.Fields.relayCount.name()))) {
            preparedStatement.setInt(insertQuery.indexOf(getColumnName(OutboundMessage.Fields.relayCount.name())),
                    message.getRelayCount());
        }
    }

    @Override
    protected String getColumnName(String fieldName) {
        return Optional.ofNullable(fieldColumnMap.get(fieldName)).orElse(null);
    }
}
