package com.consolefire.relayer.sample.outbox.data.repo;

import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.writer.MessageInsertStatementSetter;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class OutboundMessageInsertStatementSetter<ID extends Serializable>
    implements MessageInsertStatementSetter<ID, OutboundMessage<ID>> {

    @Override
    public void setProperties(PreparedStatement statement, OutboundMessage<ID> message) throws SQLException {
        statement.setString(1, message.getMessageId().toString());
        statement.setString(2, message.getGroupId());
        statement.setString(3, message.getChannelName());
        statement.setString(4, message.getPayload());
        statement.setString(5, message.getHeaders());
        statement.setString(6, message.getMetadata());
        statement.setString(7, message.getState().toString());
    }
}
